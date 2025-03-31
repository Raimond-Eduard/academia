import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './login/LoginPage';
import Dashboard from './dashboard/Dashboard';

const App = () => {
    const [user, setUser] = useState(null);

    const handleLogin = async (credentials) => {
        try{
            const response = await fetch('http://localhost:8080/api/academia/login', {
                method: 'POST',
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(credentials),
            });
            const data = await response.json();

            if(response.ok) {
                setUser(data.user);
            }else {
                console.log("Login failed.", data.message);
            }
        }catch(error){
            console.log("Login failed.", error);
        }
    };
    return (
      <Router>
          <Routes>
              <Route path="/"
                     element={!user ? <LoginPage onLogin={handleLogin} /> : <Navigate to="/dashboard" />}
                     />
              <Route
                  path="/dashboard"
                  element={user ? <Dashboard user={user}/> : <Navigate to="/"/>}
                  />
          </Routes>
      </Router>
    );
}

export default App;