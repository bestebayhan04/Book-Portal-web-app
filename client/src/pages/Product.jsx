import { useNavigate } from "react-router-dom";
import React, { useState, useEffect } from "react";
import axios from "axios";
import "../comp_css/Product.css";
import api from "../Router/api";

const Product = () => {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState("All");
    const [nameSearch, setNameSearch] = useState("");
    const [authorSearch, setAuthorSearch] = useState(""); // Renamed from sellerSearch to authorSearch
    const navigate = useNavigate();
    let userid = localStorage.getItem("userid");

    const filterProducts = (category, nameSearch, authorSearch, data) => {
        let filteredProducts = data;

        if (category !== "All") {
            filteredProducts = filteredProducts.filter(
                (product) => product.category === category
            );
        }

        if (nameSearch !== "") {
            const searchQuery = nameSearch.toLowerCase();
            filteredProducts = filteredProducts.filter((product) =>
                product.name.toLowerCase().includes(searchQuery)
            );
        }

        if (authorSearch !== "") {
            const authorQuery = authorSearch.toLowerCase();
            filteredProducts = filteredProducts.filter(
                (product) => product.author && product.author.name.toLowerCase().includes(authorQuery)
            );
        }

        setFilteredProducts(filteredProducts);
    };

    useEffect(() => {
        axios
            .get("http://127.0.0.1:8080/bookweb/products/all") // Adjusted endpoint
            .then((response) => {
                const sortedProducts = response.data.sort(
                    (a, b) => b.productId - a.productId
                );

                setProducts(sortedProducts);
                filterProducts(selectedCategory, nameSearch, authorSearch, sortedProducts);
            })
            .catch((error) => {
                console.error("Error fetching data from the API: ", error);
            });
    }, [selectedCategory, nameSearch, authorSearch]);

    const addProductToFavList = (productid) => {
        if (!userid) {
            alert("Please log in to add products to the favorite list.");
            return;
        }

        api
            .post(`/bookweb/favlist/add-product?userId=${userid}&productId=${productid}`) // Adjusted endpoint
            .then((response) => {
                localStorage.setItem("favlistid", response.data.favlistId);
                alert("Product added to favorite list.");
            })
            .catch((error) => {
                if (error.response && error.response.data) {
                    alert(error.response.data.message);
                } else {
                    alert("Error adding product to favorite list. Please try again later.");
                    console.error("Error adding product:", error);
                }
            });
    };

    const addProductToReadList = (productid) => {
        if (!userid) {
            alert("Please log in to add products to the read list.");
            return;
        }

        api
            .post(`/bookweb/readlist/add-product?userId=${userid}&productId=${productid}`) // Adjusted endpoint
            .then((response) => {
                alert("Product added to the read list.");
            })
            .catch((error) => {
                if (error.response && error.response.data) {
                    alert(error.response.data.message);
                } else {
                    alert("Error adding product to the read list. Please try again later.");
                    console.error("Error adding product:", error);
                }
            });
    };

    return (
        <div className="product-page">
            <div className="filter-section">
                <h2>Filter</h2>
                <hr />
                <br />

                <h4 style={{ marginBottom: "10px" }}>Category</h4>
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
                <br />
                <br />


                <div>
                    <h4 style={{ marginBottom: "10px" }}>By Name</h4>
                    <input
                        type="text"
                        placeholder="Search by name"
                        value={nameSearch}
                        onChange={(e) => setNameSearch(e.target.value)}
                    />
                </div>

                <br />
                <div>
                    <h4 style={{ marginBottom: "5px" }}>By Author</h4>
                    <input
                        type="text"
                        placeholder="Search by author"
                        value={authorSearch}
                        onChange={(e) => setAuthorSearch(e.target.value)}
                    />
                </div>
            </div>

            <div className="product-list">
                {filteredProducts.length === 0 ? (
                    <h1
                        style={{
                            textAlign: "center",
                            margin: "50px",
                            color: "green",
                            width: "800px",
                        }}
                    >
                        Product Not found...
                    </h1>
                ) : (
                    filteredProducts.map((product) => (
                        <div className="product-card" key={product.productId}>
                            <div className="product-image1">
                                <img src={product.imageUrl} alt={product.name} />
                            </div>
                            <div className="product-info">
                                <h2>{product.name}</h2>
                                <p>
                                    <strong>Category :</strong> {product.category}
                                </p>
                                <p>
                                    <strong>Author: </strong>
                                    {product.author ? product.author.name : "Unknown"}
                                </p>
                                <p>
                                    <strong>Description: </strong>
                                    {product.description.substring(0, 25)}
                                </p>

                                <div>
                                    <button onClick={() => addProductToFavList(product.productId)}>
                                        Add to Favorite List
                                    </button>
                                    <button onClick={() => addProductToReadList(product.productId)}>
                                        Add to Read List
                                    </button>
                                    <button onClick={() => navigate(`/product/${product.productId}`)}>
                                        View
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default Product;
