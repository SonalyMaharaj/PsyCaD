<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="MoM_Web.Login" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Sign In</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>

    <style>
        .bigblock
        {
            margin: auto;
            width: 50%;
            height:100%;
            padding: 10px;
            display: block;
        }

        .overlap
        {
            width: 50%;
            height: 100%;
            position: absolute;
            display: block;
            margin: auto;
        }         
        
        .upperblock
        {
            height: 140px;
            width:50%;
            border-radius: 15px;
            z-index: 1;
        }

        .frmlogin
        {
            border: 3px solid #f1f1f1;
            background: white;
            width: 635px;
            height: 465px;
            border-radius: 5%;
            z-index: 2;
            top: 120px; 
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

        .logo
        {    
            z-index: 3;
      	    border-radius:100%;
            box-shadow: 2px 8px 16px 2px black;
            width: 150px;
            height: 150px;
            margin-top:5%;
            position:relative;
            margin-left: 250px;
            margin-right: auto;
        } 
    </style>
</head>
<body>
    <div class="bigblock">
        
        <img class=" overlap upperblock" src="images/logingradient.jpeg" alt="image"/>

        <form class="overlap frmlogin" method="post" runat="server">

            <div class="container">
            <br /><br /><br /><br />

            <center><input type="text" placeholder="&#xf0e0; Staff Email" style="font-family:Arial, FontAwesome" name="email" required="required"/></center>
            <center><input type="password" placeholder="&#xf023; Staff Password" style="font-family:Arial, FontAwesome" name="psw" required="required"/></center>
        
            <br />
            <label>
            <input style="margin-left:150px;" type="checkbox" class="checkstyle" name="remember"/> Remember me
            </label>
            <span style="margin-right:150px;" class="psw"><a href="https://ulink.uj.ac.za/views/reset/Reset1">Forgot Password?</a></span>

            <br />
            <br />
        
            <center><asp:Button class="buttonstyle" ID="btnLogin" runat="server" Text="SIGN IN" onclick="btnLogin_Clicked"/></center>

            </div>

            <center>
            <div>
            <p style="font-size:10px;">Brought to you by PsyCaD</p>
            <img src="images/ujlogo.png" alt="Logo" style="width:80px;height:80px;"/>
            </div>
            </center>

        </form>

        <img class="logo" src="images/logocropped.png" alt="logo"/>
    </div>
</body>
</html>
