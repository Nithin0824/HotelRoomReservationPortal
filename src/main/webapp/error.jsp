<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Something Went Wrong</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f7fa;
            text-align: center;
            padding-top: 100px;
        }

        .error-box {
            width: 500px;
            margin: auto;
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }

        h1 {
            color: #d9534f;
        }

        p {
            color: #555;
            font-size: 17px;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #1976d2;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }
    </style>
</head>

<body>

<div class="error-box">

    <h1>Something Went Wrong</h1>

    <p>
        Sorry, we are temporarily unable to process your request.
        Please try again later.
    </p>

    <a href="index.jsp">Back to Home</a>

</div>

</body>
</html>