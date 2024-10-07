import React, { useState, useEffect } from "react";
import axios from "axios";
import "../comp_css/AllProductAdmin.css";
import api from "../Router/api";
import UpdateProductForm from "./UpdateProductForm";

const AllProductAdmin = () => {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const [deleted, setDeleted] = useState(false);
    const [showUpdateModal, setShowUpdateModal] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [selectedCategory, setSelectedCategory] = useState("All");
    const [priceOrder, setPriceOrder] = useState("All");
    const [nameSearch, setNameSearch] = useState("");
    const [authorSearch, setAuthorSearch] = useState(""); // Changed to match ProductDto fields

    // Filter products based on selected filters
    const filterProducts = (category, nameSearch, authorSearch, data) => {
        let filteredProducts = data;

        // Filter by category
        if (category !== "All") {
            filteredProducts = filteredProducts.filter(
                (product) => product.category === category
            );
        }


        // Filter by product name
        if (nameSearch !== "") {
            const searchQuery = nameSearch.toLowerCase();
            filteredProducts = filteredProducts.filter((product) =>
                product.name.toLowerCase().includes(searchQuery)
            );
        }

        // Filter by author (previously seller)
        if (authorSearch !== "") {
            const authorQuery = authorSearch.toLowerCase();
            filteredProducts = filteredProducts.filter(
                (product) => product.author && product.author.name.toLowerCase().includes(authorQuery)
            );
        }

        setFilteredProducts(filteredProducts);
    };

    // Fetch products on component mount and when dependencies change
    useEffect(() => {
        axios
            .get("http://127.0.0.1:8080/bookweb/products/all?sort=desc") // Adjusted endpoint
            .then((response) => {
                console.log("API Response:", response.data);
                const sortedProducts = response.data.sort(
                    (a, b) => b.productId - a.productId
                );
                setProducts(sortedProducts);
                filterProducts(selectedCategory, nameSearch, authorSearch, sortedProducts);
                setDeleted(false);
            })
            .catch((error) => {
                console.error("Error fetching data from the API:", error);
            });
    }, [deleted, selectedCategory, nameSearch, authorSearch]);

    // Handle product update
    const updateProduct = (productIdToUpdate) => {
        const productToUpdate = products.find(
            (product) => product.productId === productIdToUpdate
        );
        setSelectedProduct(productToUpdate);
        setShowUpdateModal(true);
    };

    // Close update modal
    const closeUpdateModal = () => {
        setSelectedProduct(null);
        setShowUpdateModal(false);
    };

    // Handle updated product data from modal
    const handleUpdate = (updatedProduct) => {
        api
            .put(`/bookweb/products/update/${updatedProduct.productId}`, updatedProduct)
            .then((response) => {
                console.log(response.data);
                closeUpdateModal();

                // Refresh product list after update
                axios
                    .get("http://127.0.0.1:8080/bookweb/products/all?sort=desc")
                    .then((response) => {
                        const sortedProducts = response.data.sort(
                            (a, b) => b.productId - a.productId
                        );
                        setProducts(sortedProducts);
                        filterProducts(selectedCategory, priceOrder, nameSearch, authorSearch, sortedProducts);
                    })
                    .catch((error) => {
                        console.error("Error fetching data from the API:", error);
                    });
            })
            .catch((error) => {
                console.log(error.response.data.message);
            });
    };

    // Handle product deletion
    const deleteProduct = (productIdToDelete) => {
        api
            .delete(`/bookweb/products/${productIdToDelete}`) // Adjusted endpoint
            .then((response) => {
                alert(response.data);

                const updatedProducts = products.filter(
                    (product) => product.productId !== productIdToDelete
                );
                setProducts(updatedProducts);
                setFilteredProducts(updatedProducts);
                setDeleted(true);
            })
            .catch((error) => {
                console.error("Error deleting product:", error);
                alert(error.response.data.message);
            });
    };

    return (
        <>
            <h1 style={{ color: "black", textAlign: "center", margin: "20px 0" }}>
                All Live Products
            </h1>

            <div className="filter-section">
                <h2>Filter</h2>
                <hr />
                <div className="filter-container">
                    <div className="filter-item">
                        <label>Category</label>
                        <select
                            value={selectedCategory}
                            onChange={(e) => {
                                setSelectedCategory(e.target.value);
                            }}
                        >
                            <option value="All">All</option>
                            <option value="fantastic">Fantastic</option>
                            <option value="horror">Horror</option>
                            <option value="classic">Classic</option>
                            <option value="thriller">Thriller</option>
                        </select>
                    </div>

                    <div className="filter-item" style={{ marginTop: "20px" }}>
                        <label>By Name</label>
                        <input
                            type="text"
                            placeholder="Search by name"
                            value={nameSearch}
                            onChange={(e) => setNameSearch(e.target.value)}
                        />
                    </div>
                    <div className="filter-item" style={{ marginTop: "20px" }}>
                        <label>By Author</label>
                        <input
                            type="text"
                            placeholder="Search by author"
                            value={authorSearch}
                            onChange={(e) => setAuthorSearch(e.target.value)}
                        />
                    </div>
                </div>
            </div>

            {showUpdateModal && (
                <div className="update-modal">
                    <UpdateProductForm
                        product={selectedProduct}
                        onUpdate={handleUpdate}
                        onClose={closeUpdateModal}
                    />
                </div>
            )}

            <div className="product-container1">
                {filteredProducts.length === 0 ? (
                    <h2 style={{ textAlign: "center", color: "red" }}>
                        No Products Available
                    </h2>
                ) : (
                    filteredProducts.map((product) => (
                        <div className="product-card1" key={product.productId}>
                            <div className="product-image11">
                                <img src={product.imageUrl} alt={product.name} />
                            </div>
                            <div className="product-info1">
                                <h2>{product.name}</h2>
                                <p>Product ID: {product.productId}</p>
                                <p>Category: {product.category}</p>
                                <p>Author: {product.author ? product.author.name : "Unknown"}</p> {/* Adjusted field */}
                                <p>
                                    Description:{" "}
                                    {product.description.length > 30
                                        ? product.description.substring(0, 50) + "..."
                                        : product.description}
                                </p>

                                <div className="button-container1">
                                    <button onClick={() => updateProduct(product.productId)}>
                                        Update
                                    </button>
                                    <button onClick={() => deleteProduct(product.productId)}>
                                        Delete
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </>
    );
};

export default AllProductAdmin;
