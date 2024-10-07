import React from "react";
import { Routes, Route } from "react-router-dom";
import Product from "../pages/Product";
import Login from "../pages/LogIn";
import AdminLogin from "../pages/AdminLogIn";
import Registration from "../pages/Registration";
import SingleProduct from "../pages/SingleProduct";
import ReadList from "../pages/ReadList";
import FavList from "../pages/FavList";

import { Privateroute, Privaterouteadmin } from "../Router/ProtectedRoute";
import Admin from "../pages/Admin";
import NotFound from "../components/NotFound";
import EditCustomerDetails from "../components/EditCustomerDetails";
import AdminUserDetails from "../components/AdminUserDetails";

const AllRoutes = () => {
    return (
        <>
            <Routes>
                <Route path="/" element={<Product />} />
                <Route path="/product" element={<Product />} />
                <Route path="/login" element={<Login />} />

                <Route path="/user" element={<Privateroute />}>
                    <Route path="favlist" element={<FavList />} />
                    <Route path="readlist" element={<ReadList />} />
                </Route>

                <Route path="/admin" element={<Privaterouteadmin />}>
                    <Route path="admin" element={<Admin />} />
                    <Route path="customer-details" element={<AdminUserDetails />} />
                    <Route path="edit-customer/:userId" element={<EditCustomerDetails />} /> {/* New Route */}
                </Route>

                <Route path="/admin-login" element={<AdminLogin />} />
                <Route path="/register-user" element={<Registration />} />
                <Route path="/product/:productId" element={<SingleProduct />} />
                <Route path="/products" element={<Product />} />
                <Route path="*" element={<NotFound />} />
            </Routes>
        </>
    );
};

export default AllRoutes;
