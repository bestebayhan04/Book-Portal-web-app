import React, { useState, useEffect } from "react";
import api from "../Router/api";  // Importing your API configuration
import "../comp_css/AllOrderAdmin.css";  // Assuming this CSS file contains relevant styles

const AllAuthorAdmin = () => {
    const [authors, setAuthors] = useState([]);
    const [newAuthorName, setNewAuthorName] = useState("");
    const [selectedAuthorId, setSelectedAuthorId] = useState(null);
    const [updatedAuthorName, setUpdatedAuthorName] = useState("");
    const [searchQuery, setSearchQuery] = useState("");

    // Fetch all authors on component mount
    useEffect(() => {
        api.get("http://127.0.0.1:8080/bookweb/authors/all")
            .then(response => {
                console.log("Authors fetched: ", response.data);
                setAuthors(Array.isArray(response.data) ? response.data : []);
            })
            .catch(error => {
                console.error("Error fetching authors: ", error);
                setAuthors([]);  // Fallback to empty array on error
            });
    }, []);

    // Add a new author
    const addAuthor = () => {
        api.post("http://127.0.0.1:8080/bookweb/authors/add", { name: newAuthorName })
            .then(response => {
                setAuthors([...authors, response.data]);
                setNewAuthorName("");
            })
            .catch(error => {
                console.error("Error adding author: ", error);
            });
    };

    // Update an existing author's name
    const updateAuthor = (authorId) => {
        api.put(`http://127.0.0.1:8080/bookweb/authors/update/${authorId}`, { name: updatedAuthorName })
            .then(response => {
                setAuthors(authors.map(author => author.authorId === authorId ? response.data : author));
                setSelectedAuthorId(null);
                setUpdatedAuthorName("");
            })
            .catch(error => {
                console.error("Error updating author: ", error);
            });
    };

    // Delete an author
    const deleteAuthor = (authorId) => {
        api.delete(`http://127.0.0.1:8080/bookweb/authors/delete/${authorId}`)
            .then(() => {
                setAuthors(authors.filter(author => author.authorId !== authorId));
            })
            .catch(error => {
                console.error("Error deleting author: ", error);
            });
    };

    // Filter authors based on search query
    const filteredAuthors = authors.filter(author =>
        author.name.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="author-management">
            <h1>Author Management</h1>
            <div className="add-author">
                <input
                    type="text"
                    value={newAuthorName}
                    onChange={(e) => setNewAuthorName(e.target.value)}
                    placeholder="Enter new author name"
                    className="author-input"
                />
                <button onClick={addAuthor} style={{ marginBottom: "25px" }} className="add-author-button">Add Author</button>
            </div>
            <div style={{width: "250px"}} className="search-author">
                <input
                    type="text"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    placeholder="Search author by name"
                    className="search-input"
                />
            </div>
            <ul className="author-list">
                {filteredAuthors.length === 0 ? (
                    <p>No authors found</p>
                ) : (
                    filteredAuthors.map((author) => (
                        <li key={author.authorId} className="author-item">
                            <span className="author-name">{author.name}</span>
                            <div className="author-buttons">
                                <button onClick={() => {
                                    setSelectedAuthorId(author.authorId);
                                    setUpdatedAuthorName(author.name);
                                }}>Update</button>
                                <button onClick={() => deleteAuthor(author.authorId)}>Delete</button>
                            </div>
                        </li>
                    ))
                )}
            </ul>
            {selectedAuthorId && (
                <div className="update-author">
                    <input
                        type="text"
                        value={updatedAuthorName}
                        onChange={(e) => setUpdatedAuthorName(e.target.value)}
                        placeholder="Enter new author name"
                    />
                    <button onClick={() => updateAuthor(selectedAuthorId)}>Save</button>
                    <button onClick={() => setSelectedAuthorId(null)}>Cancel</button>
                </div>
            )}
        </div>
    );
};

export default AllAuthorAdmin;
