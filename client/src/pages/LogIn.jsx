import React, { useState, useEffect } from "react";
import "../comp_css/Login.css";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import loginbg from "../picture/background_image.webp";
import {jwtDecode} from "jwt-decode"; // Corrected import for jwtDecode

const bg = {
    backgroundImage: `url(${loginbg})`,
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundPosition: "center center",
    border: "1px solid grey",
    height: "100vh",
};

const initialFormData = {
    email: "",
    password: "",
};

const Login = () => {
    const navigate = useNavigate();
    const [form, setForm] = useState(initialFormData);

    useEffect(() => {
        document.title = 'Ecommerse | LogIn';
        return () => {
            document.title = 'Ecommerse App';
        };
    }, []);

    const setHandlerChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const submitHandler = async (e) => {
        e.preventDefault();

        const params = new URLSearchParams();
        params.append("email", form.email);
        params.append("password", form.password);

        try {
            const response = await axios.post("http://localhost:8080/login", params, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
            });

            if (response.headers.authorization) {
                const token = response.headers.authorization.split(" ")[1];
                const decodedToken = jwtDecode(token);

                const userRole = decodedToken.role;
                const userId = decodedToken.userId;
                const favlistId = decodedToken.favlistId;
                const readlistId = decodedToken.readlistId;
                const name = decodedToken.firstName;

                if (userRole === 'ROLE_USER') {
                    localStorage.setItem("jwtToken", response.headers.authorization);
                    localStorage.setItem("name", name || "LogIn");
                    localStorage.setItem("userid", userId);
                    localStorage.setItem("favlistid", favlistId);
                    localStorage.setItem("readlistid", readlistId);
                    console.log("JWT Token stored:", response.headers.authorization);


                    alert("User logged in successfully");

                    navigate("/");
                } else {
                    alert("Invalid Credential");
                    navigate("/login");
                }
            } else {
                alert("Invalid Credential");
                console.error("JWT retrieval failed");
            }
        } catch (error) {
            if (error.response && error.response.status === 401) {
                alert("Invalid credentials. Please try again.");
            } else {
                alert("Error during login. Please try again later.");
                console.error("Error during login:", error);
            }
        }
    };

    const { email, password } = form;

    return (
        <>
            <div style={bg}>
                <h2 style={{ textAlign: "center", color: "grey", margin: "20px" }}>
                    WELCOME TO USER LOGIN PAGE
                </h2>
                <div className="loginConatiner">
                    <div className="login-form">
                        <h2 style={{ textAlign: "center" }}>LogIn </h2>
                        <form onSubmit={submitHandler}>
                            <div className="form-group">
                                <label htmlFor="email">Email:</label>
                                <input
                                    id="email"
                                    type="text"
                                    name="email"
                                    value={email}
                                    onChange={setHandlerChange}
                                />
                            </div>
                            <br />
                            <div className="form-group">
                                <label>Password:</label>
                                <input
                                    type="password"
                                    name="password"
                                    value={password}
                                    onChange={setHandlerChange}
                                />
                            </div>
                            <div className="form-group">
                                <input type="submit" value="Login" />
                                <p>
                                    Don't have an account?{" "}
                                    <Link to="/register-user">Register here</Link>
                                </p>
                            </div>
                        </form>
                        <p>
                            <Link to="/">Turn back to main page</Link>
                        </p>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Login;
