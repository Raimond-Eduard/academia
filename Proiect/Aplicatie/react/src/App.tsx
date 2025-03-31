import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Cookies from "js-cookie";
import LoginPage from "./login/LoginPage";
import Dashboard from "./dashboard/Dashboard";
import {AuthRequest} from './generated/auth_pb';
import {AuthServiceClient} from './generated/auth_grpc_web_pb';


const App = () => {
    const [user, setUser] = useState<string | null>(Cookies.get("jwt") || null); // Get the JWT from cookies if it exists

    const handleLogin = async (email: string, password: string) => {
        const client = new AuthServiceClient("http://localhost:5051");
        const request = new AuthRequest();
        request.setEmail(email);
        request.setPassword(password);

        // Send request to gRPC server
        client.authenticate(request, {}, (err, response) => {
            if (err) {
                console.error("Authentication failed:", err.message);
                alert("Login failed. Please check your credentials.");
            } else {
                const token = response.getToken();
                console.log("JWT Token received:", token);

                // Store the JWT token in cookies
                Cookies.set("jwt", token, { expires: 1 }); // Expires in 1 day
                setUser({email}); // Update the user state
            }
        });
    };

    return (
        <Router>
            <Routes>
                <Route
                    path="/"
                    element={
                        !user ? (
                            <LoginPage
                                onLogin={(email, password) => handleLogin(email, password)}
                            />
                        ) : (
                            <Navigate to="/dashboard" />
                        )
                    }
                />
                <Route
                    path="/dashboard"
                    element={user ? <Dashboard user={user} /> : <Navigate to="/" />}
                />
            </Routes>
        </Router>
    );
};

export default App;
