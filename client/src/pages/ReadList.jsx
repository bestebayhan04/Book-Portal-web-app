import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../Router/api";
import "../comp_css/ReadList.css";

const ReadList = () => {
    const navigate = useNavigate();
    const [readListData, setReadListData] = useState({});
    const [totalAmount, setTotalAmount] = useState(0);
    let readListId = localStorage.getItem("readlistid");
    let userId = localStorage.getItem("userid");

    // Debug: Log readListId to ensure it's being retrieved correctly
    console.log("ReadList ID:", readListId);

    const fetchReadListData = () => {
        if (!readListId) return;

        console.log("Fetching ReadList data..."); // Debugging info
        api
            .get(`/bookweb/readlist/products/${readListId}`) // Corrected endpoint
            .then((response) => {
                console.log("ReadList Data:", response.data); // Debugging info
                setReadListData(response.data);
                setTotalAmount(response.data.totalAmount || 0); // Fallback if totalAmount is undefined
            })
            .catch((error) => {
                console.error("Error fetching ReadList data from the API:", error);
                if (error.response) {
                    console.error("Error Response Data:", error.response.data);
                } else {
                    console.error("Error Message:", error.message);
                }
            });
    };

    useEffect(() => {
        document.title = "Ecommerse | ReadList";
        fetchReadListData();
    }, [readListId]); // Only depend on readListId here to prevent unnecessary updates

    const emptyReadList = () => {
        api
            .delete(`/bookweb/readlist/empty-readlist/${readListId}`) // Corrected endpoint
            .then(() => {
                setReadListData({ ...readListData, readListItems: [] });
                setTotalAmount(0);
                alert("All items removed from read list");
            })
            .catch((error) => {
                alert("Failed to empty the read list.");
                console.error("Error emptying read list:", error);
            });
    };

    const removeProductFromReadList = (productId) => {
        api
            .delete(`/bookweb/readlist/remove-product/${readListId}/${productId}`) // Corrected endpoint
            .then(() => {
                const updatedReadListItems = readListData.readListItems.filter(
                    (item) => item.product.productId !== productId
                );

                if (updatedReadListItems.length === 0) {
                    setReadListData({ ...readListData, readListItems: [] });
                    setTotalAmount(0);
                } else {
                    setReadListData({ ...readListData, readListItems: updatedReadListItems });
                    setTotalAmount(
                        updatedReadListItems.reduce(
                            (sum, item) => sum + item.product.price * item.quantity,
                            0
                        )
                    );
                }

                alert("Product removed from read list");
            })
            .catch((error) => {
                alert("Failed to remove product from read list");
                console.error("Error removing product from read list:", error);
            });
    };

    return (
        <div className="readlist-page">
            {readListData.readListItems && readListData.readListItems.length > 0 ? (
                <div className="readlist">
                    {readListData.readListItems.map((item) => (
                        <div className="readlist-card" key={item.readListItemId}>
                            <div className="readproduct-image">
                                <img src={item.product.imageUrl} alt={item.product.name} />
                            </div>
                            <div className="readproduct-info">
                                <h2>{item.product.name}</h2>
                                <p>Category: {item.product.category}</p>
                                <p>Description: {item.product.description}</p>


                                <div>
                                    <button
                                        onClick={() => {removeProductFromReadList(item.product.productId);
                                            if (readListData.readListItems.length === 1) {
                                                setReadListData({ ...readListData, readListItems: [] });
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
                <div className="empty-readlist-message">
                    <h1>
                        Your read list is empty. <Link to="/">Add Now</Link>
                    </h1>
                </div>
            )}

            <div className="readlist-details">
                <div className="counter-box">
                    <div>
                        <button
                            onClick={() => {
                                emptyReadList();
                                setReadListData({ ...readListData, readListItems: [] });
                            }}
                            className="action-button"
                        >
                            Empty Read List
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

export default ReadList;
