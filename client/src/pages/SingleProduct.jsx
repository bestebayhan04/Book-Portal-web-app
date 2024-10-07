import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from '../Router/api';
import "../comp_css/SingleProduct.css";
import axios from "axios";

const SingleProduct = () => {
    const navigate = useNavigate();
    const { productId } = useParams();
    const [product, setProduct] = useState({});
    const userid = localStorage.getItem("userid");

    useEffect(() => {
        axios
            .get(`http://127.0.0.1:8080/bookweb/products/${productId}`) // Corrected endpoint
            .then((response) => {
                setProduct(response.data);
            })
            .catch((error) => {
                console.error("Error fetching data from the API: ", error);
            });
    }, [productId]);

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
                alert("Product already in favorite list.");
                console.error("Error adding product to favorite list:", error);
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
                alert("Error adding product to the read list. Please try again.");
                console.error("Error details: ", error);
            });
    };

    return (
        <>
            <h1 style={{ color: "green", textAlign: "center", margin: "20px", fontSize: "2.5em", fontWeight: "lighter" }}>
                Single Product
            </h1>
            <div className="product-container">
                <div className="product_image">
                    <img src={product.imageUrl} alt={product.name} />
                </div>

                <div className="product_details">
                    <h2>{product.name}</h2>
                    <p>Category: {product.category}</p>
                    <p>Description: {product.description}</p>
                    <p>Author: {product.author ? product.author.name : "Unknown"}</p> {/* Adjusted field */}

                    <div>
                        <button
                            onClick={() => {
                                addProductToFavList(product.productId);
                            }}
                        >
                            Add to Favorite List
                        </button>
                    </div>
                    <div>
                        <button
                            onClick={() => {
                                addProductToReadList(product.productId);
                            }}
                        >
                            Add to Read List
                        </button>
                    </div>

                    <div style={{ marginTop: "20px" }}>
                        <button
                            onClick={() => {
                                navigate("/");
                            }}
                        >
                            Go Back to Main Page
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
};

export default SingleProduct;
