import * as grpcWeb from 'grpc-web';

import * as auth_pb from './auth_pb'; // proto import: "auth.proto"


export class AuthServiceClient {
  constructor (hostname: string,
               credentials?: null | { [index: string]: string; },
               options?: null | { [index: string]: any; });

  authenticate(
    request: auth_pb.AuthRequest,
    metadata: grpcWeb.Metadata | undefined,
    callback: (err: grpcWeb.RpcError,
               response: auth_pb.AuthResponse) => void
  ): grpcWeb.ClientReadableStream<auth_pb.AuthResponse>;

  validateToken(
    request: auth_pb.TokenRequest,
    metadata: grpcWeb.Metadata | undefined,
    callback: (err: grpcWeb.RpcError,
               response: auth_pb.ValidationResponse) => void
  ): grpcWeb.ClientReadableStream<auth_pb.ValidationResponse>;

  destroyToken(
    request: auth_pb.TokenRequest,
    metadata: grpcWeb.Metadata | undefined,
    callback: (err: grpcWeb.RpcError,
               response: auth_pb.DestroyResponse) => void
  ): grpcWeb.ClientReadableStream<auth_pb.DestroyResponse>;

}

export class AuthServicePromiseClient {
  constructor (hostname: string,
               credentials?: null | { [index: string]: string; },
               options?: null | { [index: string]: any; });

  authenticate(
    request: auth_pb.AuthRequest,
    metadata?: grpcWeb.Metadata
  ): Promise<auth_pb.AuthResponse>;

  validateToken(
    request: auth_pb.TokenRequest,
    metadata?: grpcWeb.Metadata
  ): Promise<auth_pb.ValidationResponse>;

  destroyToken(
    request: auth_pb.TokenRequest,
    metadata?: grpcWeb.Metadata
  ): Promise<auth_pb.DestroyResponse>;

}

