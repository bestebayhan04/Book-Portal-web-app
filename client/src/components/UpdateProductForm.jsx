import React, { useState } from "react";
import "../comp_css/UpdateForm.css";

const UpdateProductForm = ({ product, onUpdate, onClose }) => {
    // Initialize the updatedProduct state with product data including authorname
    const [updatedProduct, setUpdatedProduct] = useState({
        ...product,
        authorname: product.author ? product.author.name : "" // Use author instead of seller
    });

    const handleChange = (e) => {
        const { name, value } = e.target;

        // Handle changes specifically for authorname
        if (name === "authorname") {
            setUpdatedProduct({
                ...updatedProduct,
                author: { ...updatedProduct.author, name: value },
                authorname: value // Update authorname in the state
            });
        } else {
            setUpdatedProduct({ ...updatedProduct, [name]: value });
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Pass updated product data to onUpdate callback
        onUpdate(updatedProduct);
    };

    return (
        <>
            <div className="modal-backdrop">
                <div className="update-product-form">
                    <span className="close-button" onClick={onClose}>
                        &times;
                    </span>
                    <h2>Update Product</h2>
                    <form onSubmit={handleSubmit}>
                        <div>
                            <label htmlFor="name">Product Name:</label>
                            <input
                                type="text"
                                name="name"
                                value={updatedProduct.name}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div>
                            <label htmlFor="imageUrl">Image URL:</label>
                            <input
                                type="text"
                                name="imageUrl"
                                value={updatedProduct.imageUrl}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div>
                            <label htmlFor="description">Description:</label>
                            <input
                                type="text"
                                name="description"
                                value={updatedProduct.description}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="category">Category:</label>
                            <select
                                name="category"
                                value={updatedProduct.category}
                                onChange={handleChange}
                                required
                            >
                                <option value="fantastic">Fantastic</option>
                                <option value="horror">Horror</option>
                                <option value="classic">Classic</option>
                                <option value="thriller">Thriller</option>
                            </select>
                        </div>
                        <div>
                            <label htmlFor="authorname">Author Name:</label> {/* Adjusted label */}
                            <input
                                type="text"
                                name="authorname"
                                value={updatedProduct.authorname}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <button type="submit">Update</button>
                        <button type="submit" onClick={onClose}>Cancel</button>
                    </form>
                </div>
            </div>
        </>
    );
};

export default UpdateProductForm;
