from enum import Enum
from typing import AbstractSet

import grpc
from concurrent import futures

from jwt import exceptions, JWT, AbstractJWKBase
from jwt.jwk import OctetJWK

import auth_pb2
import auth_pb2_grpc
import jwt
import time
import uuid

from models import User
from database import db

db.connect()

SECRET_KEY = '2109'.encode('utf-8')
ISSUER = "http://localhost:5051"
EXPIRATION_TIME = 1600
BLACKLIST = set()

USERS = {}

for entry in User.select():
    USERS[entry.email] = {"password": entry.password,
                          "id": entry.uid,
                          "role": entry.role}


class AuthService(auth_pb2_grpc.AuthServiceServicer):

    def Authenticate(self, request, context):

        email = request.email
        password = request.password

        # Get user from the USERS dictionary
        user = USERS.get(email)

        if not user or password != user["password"]:
            context.set_code(grpc.StatusCode.UNAUTHENTICATED)
            context.set_details('Invalid credentials')
            return auth_pb2.AuthResponse()

        # Prepare payload for JWT encoding with integer expiration time
        payload = {
            "iss": ISSUER,
            "exp": int(time.time() + EXPIRATION_TIME),  # Expiration time must be an integer
            "sub": email,
            "jti": str(uuid.uuid4()),
            "role": user["role"]
        }

        # Encode JWT
        token = JWT()
        key = OctetJWK(SECRET_KEY)  # Convert the SECRET_KEY to an OctetJWK object
        encoded_token = token.encode(payload, key=key, alg='HS256')

        return auth_pb2.AuthResponse(token=encoded_token)

    def ValidateToken(self, request, context):
        token = request.token
        print(token)

        # Check if token is blacklisted
        if token in BLACKLIST:
            context.set_code(grpc.StatusCode.UNAUTHENTICATED)
            context.set_details('Token is blacklisted')
            return auth_pb2.ValidateResponse()

        try:
            # Decode JWT token
            key = OctetJWK(SECRET_KEY)  # Key must be an OctetJWK instance
            payload = JWT().decode(token, key=key, algorithms={"HS256"})
            print(payload)
            return auth_pb2.ValidationResponse(sub=payload['sub'], role=payload['role'])
        except exceptions.InvalidKeyTypeError:
            context.set_code(grpc.StatusCode.UNAUTHENTICATED)
            context.set_details('Token is invalid')
            return auth_pb2.ValidationResponse()


    def DestroyToken(self, request, context):
        token = request.token
        BLACKLIST.add(token)
        return auth_pb2.DestroyResponse(message="Token invalidated successfully")

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    auth_pb2_grpc.add_AuthServiceServicer_to_server(AuthService(), server)

    server.add_insecure_port('[::]:5051')
    server.start()
    server.wait_for_termination()
