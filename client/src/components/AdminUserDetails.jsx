import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../comp_css/AdminUserDetails.css';
import api from '../Router/api';

function AdminUserDetails() {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        api
            .get('/bookweb/customers/get-all-customer') // Fetch all users
            .then((response) => {
                setUsers(response.data);
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
                setLoading(false);
            });
    }, []);

    // Function to handle the deletion of a user
    const deleteUser = (userId) => {
        if (window.confirm('Are you sure you want to delete this user?')) {
            api
                .delete(`/bookweb/customers/delete/${userId}`) // Delete user by ID
                .then(() => {
                    setUsers(users.filter(user => user.userId !== userId));
                    alert('User deleted successfully');
                })
                .catch((error) => {
                    console.error('Error deleting user:', error);
                    alert('Failed to delete user');
                });
        }
    };

    return (
        <div className="admin-users">
            {loading ? (
                <p>Loading...</p>
            ) : (
                users.map((user) => (
                    <div className="user-card" key={user.userId}>
                        <div className="user-info">
                            <h3>User Details</h3>
                            <p>User ID: {user.userId}</p>
                            <p>Email: {user.email}</p>
                            <p>Name: {user.firstName} {user.lastName}</p>
                            <p>Register Time: {new Date(user.registerTime).toLocaleString()}</p>
                        </div>
                        <div className="user-actions">
                            <button
                                onClick={() => navigate(`/admin/edit-customer/${user.userId}`)}
                                className="edit-button"
                                className="action-button"

                            >
                                Edit
                            </button>
                            <button
                                onClick={() => deleteUser(user.userId)}
                                className="delete-button"
                                className="action-button"

                            >
                                Delete
                            </button>
                        </div>
                    </div>
                ))
            )}
        </div>
    );
}

export default AdminUserDetails;
