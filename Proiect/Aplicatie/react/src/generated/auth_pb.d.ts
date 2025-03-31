import * as jspb from 'google-protobuf'



export class AuthRequest extends jspb.Message {
  getEmail(): string;
  setEmail(value: string): AuthRequest;

  getPassword(): string;
  setPassword(value: string): AuthRequest;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AuthRequest.AsObject;
  static toObject(includeInstance: boolean, msg: AuthRequest): AuthRequest.AsObject;
  static serializeBinaryToWriter(message: AuthRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AuthRequest;
  static deserializeBinaryFromReader(message: AuthRequest, reader: jspb.BinaryReader): AuthRequest;
}

export namespace AuthRequest {
  export type AsObject = {
    email: string,
    password: string,
  }
}

export class AuthResponse extends jspb.Message {
  getToken(): string;
  setToken(value: string): AuthResponse;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AuthResponse.AsObject;
  static toObject(includeInstance: boolean, msg: AuthResponse): AuthResponse.AsObject;
  static serializeBinaryToWriter(message: AuthResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AuthResponse;
  static deserializeBinaryFromReader(message: AuthResponse, reader: jspb.BinaryReader): AuthResponse;
}

export namespace AuthResponse {
  export type AsObject = {
    token: string,
  }
}

export class TokenRequest extends jspb.Message {
  getToken(): string;
  setToken(value: string): TokenRequest;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): TokenRequest.AsObject;
  static toObject(includeInstance: boolean, msg: TokenRequest): TokenRequest.AsObject;
  static serializeBinaryToWriter(message: TokenRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): TokenRequest;
  static deserializeBinaryFromReader(message: TokenRequest, reader: jspb.BinaryReader): TokenRequest;
}

export namespace TokenRequest {
  export type AsObject = {
    token: string,
  }
}

export class ValidationResponse extends jspb.Message {
  getSub(): string;
  setSub(value: string): ValidationResponse;

  getRole(): string;
  setRole(value: string): ValidationResponse;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ValidationResponse.AsObject;
  static toObject(includeInstance: boolean, msg: ValidationResponse): ValidationResponse.AsObject;
  static serializeBinaryToWriter(message: ValidationResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ValidationResponse;
  static deserializeBinaryFromReader(message: ValidationResponse, reader: jspb.BinaryReader): ValidationResponse;
}

export namespace ValidationResponse {
  export type AsObject = {
    sub: string,
    role: string,
  }
}

export class DestroyResponse extends jspb.Message {
  getMessage(): string;
  setMessage(value: string): DestroyResponse;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): DestroyResponse.AsObject;
  static toObject(includeInstance: boolean, msg: DestroyResponse): DestroyResponse.AsObject;
  static serializeBinaryToWriter(message: DestroyResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): DestroyResponse;
  static deserializeBinaryFromReader(message: DestroyResponse, reader: jspb.BinaryReader): DestroyResponse;
}

export namespace DestroyResponse {
  export type AsObject = {
    message: string,
  }
}

