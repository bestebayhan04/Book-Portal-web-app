import React, { useState } from "react";
import api from "../Router/api";
import "../comp_css/AddProduct.css";
import { useNavigate } from "react-router-dom";

function AddProduct() {
    const navigate = useNavigate();
    const [product, setProduct] = useState({
        name: "",
        imageUrl: "",
        description: "",
        category: "",
        authorname: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProduct({ ...product, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await api.post("/bookweb/products/add", product);
            console.log("Product added successfully:", response.data);
            setProduct({
                name: "",
                imageUrl: "",
                description: "",
                category: "",
                authorname: "",
            });
            alert("Product Added Successfully.");
            navigate("/admin/admin");
        } catch (error) {
            alert(error.response?.data?.message || "An error occurred while adding the product.");
            console.error("Error adding product:", error.response?.data);
        }
    };

    return (
        <div className="adminAddProduct">
            <h2>Add Product</h2>
            <form onSubmit={handleSubmit}>
                <div className="input-group">
                    <label htmlFor="name">Product Name:</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={product.name}
                        onChange={handleChange}
                        placeholder="Enter product name"
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="imageUrl">Image URL:</label>
                    <input
                        type="text"
                        id="imageUrl"
                        name="imageUrl"
                        value={product.imageUrl}
                        onChange={handleChange}
                        placeholder="Enter image URL"
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="description">Description:</label>
                    <input
                        type="text"
                        id="description"
                        name="description"
                        value={product.description}
                        onChange={handleChange}
                        placeholder="Enter product description"
                        required
                    />
                </div>
                <div className="input-group select">
                    <label htmlFor="category">Category:</label>
                    <select
                        id="category"
                        name="category"
                        value={product.category}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select a category</option>
                        <option value="fantastic">Fantastic</option>
                        <option value="horror">Horror</option>
                        <option value="classic">Classic</option>
                        <option value="thriller">Thriller</option>
                    </select>
                </div>
                <div className="input-group">
                    <label htmlFor="authorname">Author Name:</label>
                    <input
                        type="text"
                        id="authorname"
                        name="authorname"
                        value={product.authorname}
                        onChange={handleChange}
                        placeholder="Enter author name"
                        required
                    />
                </div>

                <button type="submit">Add Product</button>
            </form>
        </div>
    );
}

export default AddProduct;
