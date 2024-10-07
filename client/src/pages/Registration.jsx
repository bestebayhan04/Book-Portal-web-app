import React, { useState } from "react";
import "../comp_css/Login.css";
import { useNavigate, Link } from "react-router-dom";
import axios from 'axios';
import loginbg from "../picture/background3.jpg";

const bg = {
    backgroundImage: `url(${loginbg})`,
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundPosition: "center center",
    border: "1px solid grey",
    height: "fit-content",
};

const initialFormData = {
    email: "",
    password: "",
    firstName: "",
    lastName: "",
};

const Registration = () => {
    const navigate = useNavigate();
    const [form, setForm] = useState(initialFormData);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post("http://localhost:8080/bookweb/customers", form); // Corrected endpoint

            if (response.status === 200 || response.status === 201) {
                alert("Your registration was successful");
                navigate("/login");
            } else {
                console.error("Registration failed");
                alert("Registration failed. Please try again.");
            }
        } catch (error) {
            if (error.response && error.response.data) {
                alert(error.response.data.message);
            } else {
                alert("Error registering. Please try again later.");
                console.error("Error registering:", error);
            }
        }
    };

    const { email, password, firstName, lastName } = form;

    return (
        <div className="login-form1" style={bg}>
            <div className="login-form">
                <h2 style={{ textAlign: "center" }}>Registration</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Email:</label>
                        <input
                            type="text" // Changed to email input type for validation
                            name="email"
                            value={email}
                            onChange={handleInputChange}
                            required // Added required for validation
                        />
                    </div>
                    <div className="form-group">
                        <label>Password:</label>
                        <input
                            type="password"
                            name="password"
                            value={password}
                            onChange={handleInputChange}
                            required // Added required for validation
                        />
                    </div>
                    <div className="form-group">
                        <label>First Name:</label>
                        <input
                            type="text"
                            name="firstName"
                            value={firstName}
                            onChange={handleInputChange}
                            required // Added required for validation
                        />
                    </div>
                    <div className="form-group">
                        <label>Last Name:</label>
                        <input
                            type="text"
                            name="lastName"
                            value={lastName}
                            onChange={handleInputChange}
                            required // Added required for validation
                        />
                    </div>
                    <div className="form-group">
                        <input type="submit" value="Register" />
                        <p>
                            Have an account?{" "}
                            <Link to="/login">Login here</Link>
                        </p>
                    </div>
                </form>

                <p>
                    <Link to="/">Turn back to main page</Link>
                </p>
            </div>
        </div>
    );
};

export default Registration;
