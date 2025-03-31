declare module "./generated/auth_pb.js" {
    export class AuthRequest {
        setEmail(email: string): void;
        setPassword(password: string): void;
    }
}

declare module "./generated/auth_grpc_web_pb.js" {
    export class AuthServiceClient {
        constructor(address: string, credentials?: null, options?: object);
        authenticate(request: AuthRequest, metadata: object, callback: (err: any, response: any) => void): void;
    }
}
