import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../Router/api';
import '../comp_css/AdminEditUser.css';

const AdminEditUser = () => {
    const { userId } = useParams(); // Fetch userId from the URL parameters
    const navigate = useNavigate();
    const [user, setUser] = useState({
        firstName: '',
        lastName: '',
        email: '',
    });

    // Fetch user details on component mount
    useEffect(() => {
        api.get(`/bookweb/customers/${userId}`) // Corrected endpoint
            .then(response => {
                setUser(response.data);
            })
            .catch(error => {
                console.error('Error fetching user data:', error);
            });
    }, [userId]);

    // Handle input changes
    const handleChange = (e) => {
        setUser({
            ...user,
            [e.target.name]: e.target.value,
        });
    };

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();
        api.put(`/bookweb/customers/update/${userId}`, user) // Corrected endpoint
            .then(() => {
                alert('User details updated successfully');
                navigate('/admin/view-all-users'); // Navigate to the users list page
            })
            .catch(error => {
                console.error('Error updating user details:', error);
                alert('Failed to update user details');
            });
    };

    return (
        <div className="edit-user-page">
            <h1>Edit User</h1>
            <form onSubmit={handleSubmit} className="edit-user-form">
                <label>
                    First Name:
                    <input
                        type="text"
                        name="firstName"
                        value={user.firstName}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Last Name:
                    <input
                        type="text"
                        name="lastName"
                        value={user.lastName}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Email:
                    <input
                        type="email"
                        name="email"
                        value={user.email}
                        onChange={handleChange}
                        disabled // Email is disabled as it should not be editable
                    />
                </label>

                <button type="submit" className="save-button">
                    Save Changes
                </button>
            </form>
            <button onClick={() => navigate('/admin/view-all-users')} className="back-button">
                Go Back to Users List
            </button>
        </div>
    );
};

export default AdminEditUser;
