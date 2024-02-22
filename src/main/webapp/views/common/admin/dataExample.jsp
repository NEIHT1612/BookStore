<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : dataExample
    Created on : Oct 18, 2023, 12:48:11 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="card mb-3">
    <div class="card-header">
        <i class="fas fa-table"></i>
        Data Table Example
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" cellspacing="0">
                <thead>
                    <tr>
                        <th id="nameHeader">Name</th>
                        <th id="imageHeader">Image</th>
                        <th id="authorHeader">Author</th>
                        <th id="priceHeader">Price</th>
                        <th id="quantityHeader">Quantity</th>
                        <th id="descriptionHeader">Description</th>
                        <th id="actionHeader">Action</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th id="nameFooter">Name</th>
                        <th id="imageFooter">Image</th>
                        <th id="authorFooter">Author</th>
                        <th id="priceFooter">Price</th>
                        <th id="quantityFooter">Quantity</th>
                        <th id="descriptionFooter">Description</th>
                        <th id="actionFooter">Action</th>
                    </tr>
                </tfoot>
                <tbody>
                    <c:forEach var="book" items="${listBook}">
                        <tr>
                            <!--Name-->
                            <td>${book.name}</td>
                            <!--Image-->
                            <td>
                                <img width="100px" height="100px" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." class="card-img-top">
                            </td>
                            <!--Author-->
                            <td>${book.author}</td>
                            <!--Price-->
                            <td>${book.price}</td>
                            <!--Quantity-->
                            <td>${book.quantity}</td>
                            <!--Description-->
                            <td>${book.description}</td>
                            <td>
                                <!--Edit-->
                                <i class="fa fa-edit fa-2x" 
                                   style="color: #469408" 
                                   data-toggle="modal" 
                                   data-target="#editBookModal"
                                   onclick="editBookModal(
                                   ${book.id},
                                                   `${book.name}`,
                                                   `${book.description}`,
                                                   `${book.author}`,
                                   ${book.price},
                                   ${book.quantity},
                                                   `${book.image}`,
                                   ${book.category_id})">

                                </i>
                                &nbsp;&nbsp;&nbsp;
                                <!--Delete-->
                                <i class="fa fa-trash fa-2x" 
                                   style="color: #e70808" 
                                   data-toggle="modal" 
                                   data-target="#delete-modal" 
                                   onclick="deleteBookModal(${book.id})">
                                </i>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
</div>
