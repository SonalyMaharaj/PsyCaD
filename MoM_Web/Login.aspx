<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="MoM_Web.Login" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Login</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>

    <style>
        .box
        {
            width: 50%;
            height: 100%;
            position: absolute;
            display: block;
            margin-left: 25%;
            margin-right: auto;
        }

        .frmlogin {
            border: 3px solid #f1f1f1;
            background: white;
            width: 635px;
            height: 475px;
            border-radius: 5%;
            z-index: 2;
            top: 120px; 
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
        }

        .imggradient {
            height: 140px;
            width:50%;
            border-radius: 15px;
            z-index: 1;
        }

        input[type=text], input[type=password] 
        {
          width: 300px;
          padding: 12px 20px;
          margin: 8px 0;
          display: inline-block;
          border: 1px solid #ccc;
          border-radius:20px;
          box-sizing: border-box;
        }

        .buttonstyle
        {
          background-color: #5023af;
          color: white;
          padding: 14px 20px;
          margin: 8px 0;
          border: none;
          border-radius:20px;
          cursor: pointer;
          width: 200px;
        }

        .buttonstyle:hover 
        {
          opacity: 0.8;
        }

        .circle
        {
            width: 150px;
            height: 150px;
            position: absolute;
            margin-left: 280px;
            margin-right: auto;
            background-color:white;
            
        }

        img.avatar
        {
            top: 40px;      
            z-index: 3;
      	    border-radius:100%;
            box-shadow: 2px 8px 16px 2px black;
            
            display:inline-block;
            overflow: hidden;
            object-fit: cover;
            width:100%;
            height:100%;
        }

        .container 
        {
          padding: 16px;
        }

        span.psw 
        {
          float: right;
          display: inline-block;
        }

        .checkstyle:checked
        {
            color:#f6660a;
        }

        a 
        {
          color: #f6660a;
        }

        /* Change styles for span and cancel button on extra small screens */
        @media screen and (max-width: 300px) 
        {
          span.psw 
          {
             display: block;
             float: none;
          }
        }
    </style>

</head>
<body>


    <img class="box imggradient" src="images/logingradient.jpeg" alt="logo"/>

    <div class="circle">
        <img src="images/logo.png" alt="Logo" class="circle avatar"/>
    </div>

    <form class=" box frmlogin" method="post" runat="server">

            

        <div class="container">
            <br />
            <br />
            <br />
            <br />

        <center><input type="text" placeholder="&#xf0e0; Staff Email" style="font-family:Arial, FontAwesome" name="email" required="required"/></center>
        <center><input type="password" placeholder="&#xf023; Staff Password" style="font-family:Arial, FontAwesome" name="psw" required="required"/></center>
        
            <br />
        <label>
            <input style="margin-left:150px;" type="checkbox" class="checkstyle" name="remember"/> Remember me
        </label>
            <span style="margin-right:150px;" class="psw"><a href="https://ulink.uj.ac.za/views/reset/Reset1">Forgot Password?</a></span>

            <br />
            <br />
        
            <center><asp:Button class="buttonstyle" ID="btnLogin" runat="server" Text="SIGN IN" OnClick="btnLogin_Clicked"/></center>

        </div>

        <center>
            <div>
                <p style="font-size:10px;">Brought to you by PsyCaD</p>
                <img src="images/ujlogo.png" alt="Logo" style="width:80px;height:80px;"/>
            </div>
        </center>

    </form>
</body>
</html>
