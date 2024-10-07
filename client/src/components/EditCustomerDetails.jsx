import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../Router/api';
import '../comp_css/AddProduct.css'; // Reusing AddProduct.css for consistency

const EditCustomerDetails = () => {
    const { userId } = useParams(); // Get the userId from the URL parameters
    const navigate = useNavigate();

    const [customer, setCustomer] = useState({
        email: '',
        firstName: '',
        lastName: '',
        registerTime: '',
    });

    useEffect(() => {
        if (userId) {
            // Fetch the customer details using the userId from the URL
            api.get(`/bookweb/customers/${userId}`) // Updated endpoint
                .then((response) => {
                    setCustomer(response.data);
                })
                .catch((error) => {
                    console.error('Error fetching customer details:', error);
                });
        }
    }, [userId]); // Re-run this effect when the userId changes

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCustomer({
            ...customer,
            [name]: value,
        });
    };

    const handleSave = () => {
        // Call API to update customer details
        api.put(`/bookweb/customers/update/${userId}`, customer) // Updated endpoint
            .then(() => {
                alert('Customer details updated successfully.');
                navigate('/admin/admin'); // Navigating back to admin main page
            })
            .catch((error) => {
                console.error('Error updating customer details:', error.response || error);
                if (error.response) {
                    alert(`Failed to update customer details: ${error.response.data.message || 'Unknown error'}`);
                } else {
                    alert('Failed to update customer details.');
                }
            });
    };

    return (
        <div className="adminAddProduct"> {/* Reuse AddProduct styles for a consistent look */}
            <h2>Edit Customer Details</h2>
            <form>
                <div className="input-group">
                    <label>Email:</label>
                    <input
                        type="text"
                        name="email"
                        value={customer.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label>First Name:</label>
                    <input
                        type="text"
                        name="firstName"
                        value={customer.firstName}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label>Last Name:</label>
                    <input
                        type="text"
                        name="lastName"
                        value={customer.lastName}
                        onChange={handleChange}
                        required
                    />
                </div>


                <div className="button-group">
                    <button className="custom-button" type="button" onClick={handleSave}>
                        Save Changes
                    </button>
                    <button
                        className="custom-button"
                        type="button"
                        onClick={() => navigate('/admin/admin')} // Go back to admin main page
                        style={{ marginTop: '10px' }} // Add some space between the buttons
                    >
                        Go Back to Main Page
                    </button>
                </div>
            </form>
        </div>
    );
};

export default EditCustomerDetails;
