import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../Router/api";
import "../comp_css/FavList.css";

const FavList = () => {
    const navigate = useNavigate();
    const [favListData, setFavListData] = useState({});
    const [totalAmount, setTotalAmount] = useState(0);
    let favListId = localStorage.getItem("favlistid");
    let userId = localStorage.getItem("userid");

    // Debug: Log favListId to ensure it's being retrieved correctly
    console.log("FavList ID:", favListId);

    const fetchFavListData = () => {
        if (!favListId) return;

        console.log("Fetching FavList data..."); // Debugging info
        api
            .get(`/bookweb/favlist/products/${favListId}`) // Corrected endpoint
            .then((response) => {
                console.log("FavList Data:", response.data); // Debugging info
                setFavListData(response.data);
                setTotalAmount(response.data.totalAmount || 0); // Fallback if totalAmount is undefined
            })
            .catch((error) => {
                console.error("Error fetching FavList data from the API:", error);
                if (error.response) {
                    console.error("Error Response Data:", error.response.data);
                } else {
                    console.error("Error Message:", error.message);
                }
            });
    };

    useEffect(() => {
        document.title = "Ecommerse | FavList";
        fetchFavListData();
    }, [favListId]); // Only depend on favListId here to prevent unnecessary updates

    const emptyFavList = () => {
        api
            .delete(`/bookweb/favlist/empty-favlist/${favListId}`) // Corrected endpoint
            .then(() => {
                setFavListData({ ...favListData, favListItems: [] });
                setTotalAmount(0);
                alert("All items removed from favorite list");
            })
            .catch((error) => {
                alert("Failed to empty the favorite list.");
                console.error("Error emptying favorite list:", error);
            });
    };

    const removeProductFromFavList = (productId) => {
        api
            .delete(`/bookweb/favlist/remove-product/${favListId}/${productId}`) // Corrected endpoint
            .then(() => {
                const updatedFavListItems = favListData.favListItems.filter(
                    (item) => item.product.productId !== productId
                );

                if (updatedFavListItems.length === 0) {
                    setFavListData({ ...favListData, favListItems: [] });
                    setTotalAmount(0);
                } else {
                    setFavListData({ ...favListData, favListItems: updatedFavListItems });
                    setTotalAmount(
                        updatedFavListItems.reduce(
                            (sum, item) => sum + item.product.price * item.quantity,
                            0
                        )
                    );
                }

                alert("Product removed from favorite list");
            })
            .catch((error) => {
                alert("Failed to remove product from favorite list");
                console.error("Error removing product from favorite list:", error);
            });
    };

    return (
        <div className="favlist-page">
            {favListData.favListItems && favListData.favListItems.length > 0 ? (
                <div className="favlist">
                    {favListData.favListItems.map((item) => (
                        <div className="favlist-card" key={item.favListItemId}>
                            <div className="favproduct-image">
                                <img src={item.product.imageUrl} alt={item.product.name} />
                            </div>
                            <div className="favproduct-info">
                                <h2>{item.product.name}</h2>
                                <p>Category: {item.product.category}</p>
                                <p>Description: {item.product.description}</p>


                                <div>
                                    <button
                                        onClick={() => {removeProductFromFavList(item.product.productId);
                                            if (favListData.favListItems.length === 1) {
                                                setFavListData({ ...favListData, favListItems: [] });
                                            }

                                    }}
                                    >
                                        Remove
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <div className="empty-favlist-message">
                    <h1>
                        Your favorite list is empty. <Link to="/">Add Now</Link>
                    </h1>
                </div>
            )}

            <div className="favlist-details">
                <div className="counter-box">
                    <div>
                        <button
                            onClick={() => {
                                emptyFavList();
                                setFavListData({ ...favListData, favListItems: [] });
                            }}
                            className="action-button"

                        >
                            Empty Favorite List
                        </button>
                    </div>

                    <div>
                        <button
                            onClick={() => {
                                navigate("/");
                            }}
                            className="action-button"

                        >
                            Go back to main page
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default FavList;
