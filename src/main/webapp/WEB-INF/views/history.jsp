<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>書籍の詳細｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/lightbox.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/lightbox.js" /></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>書籍の履歴</h1>
        <div class="d-flex align-items-center justify-content-center">
            <div class="col-5 ml-3">
                <table class="table table-bordered ml-3">
                    <thead>
                        <tr>
                        <tr class="table-primary">
                            <th>書籍名</th>
                            <th>貸出日</th>
                            <th>返却日</th>
                        </tr>
                    </thead>
                    <c:forEach var="historyBookInfo" items="${rentbookList}">
                        <tr>
                            <td>
                                <form method="get" action="<%=request.getContextPath()%>/updatehistory">
                                    <a href="javascript:void(0)" onclick="this.parentNode.submit();">${historyBookInfo.title}</a> <input type="hidden" name="bookId" value="${historyBookInfo.bookId}">
                                </form>
                            </td>
                            <td class="book_rentdate">${historyBookInfo.rentdate}</td>
                            <td class="book_returndate">${historyBookInfo.returndate}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </main>
</body>
</html>