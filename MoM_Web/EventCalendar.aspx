<%@ Page Title="" Language="C#" MasterPageFile="~/MindOverMatter.Master" AutoEventWireup="true" CodeBehind="EventCalendar.aspx.cs" Inherits="MoM_Web.EventCalendar" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

    <style> 
        body {font-family: Arial, Helvetica, sans-serif;}
        * {box-sizing: border-box;}

        /* Button used to open the contact form - fixed at the bottom of the page */
        .open-button 
        {
          background-color: #f6660a;
          color: black;
          padding: 16px 20px;
          border: none;
          cursor: pointer;
          opacity: 0.8;
          position: fixed;
          bottom: 23px;
          right: 28px;
          width: 280px;
        }

        /* The popup form - hidden by default */
        .form-popup 
        {
          display: none;
          position: fixed;
          bottom: 0;
          right: 15px;
          border: 3px solid #f1f1f1;
          z-index: 9;
        }

        /* Add styles to the form container */
        .form-container 
        {
          max-width: 300px;
          padding: 10px;
          background-color: white;
        }

        /* Full-width input fields */
        .form-container input[type=text], .form-container input[type=password] 
        {
          width: 100%;
          padding: 10px;
          margin: 5px 0 22px 0;
          border: none;
          background: #f1f1f1;
        }

        /* When the inputs get focus, do something */
        .form-container input[type=text]:focus, .form-container input[type=password]:focus 
        {
          background-color: #ddd;
          outline: none;
        }

        /* Set a style for the submit/login button */
        .form-container .btn 
        {
          background-color: #f6660a;
          color: white;
          padding: 16px 20px;
          border: none;
          cursor: pointer;
          width: 100%;
          margin-bottom:10px;
          opacity: 0.8;
        }

        /* Add a red background color to the cancel button */
        .form-container .cancel 
        {
          background-color: #5023af;
        }

        /* Add some hover effects to buttons */
        .form-container .btn:hover, .open-button:hover 
        {
          opacity: 1;
        }
    </style>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <center><h2 class="mb-4" style="color:#5023af">Event Calendar</h2></center>
    
    <button class="open-button" onclick="openForm()">Add New Event</button>

    <div class="form-popup" id="myForm">
      <form action="/action_page.php" class="form-container">
        <h2>Event Details</h2>

        <label for="email"><b>Event Name</b></label>
        <input type="text" placeholder="Enter Name" name="name" required>

        <label for="psw"><b>Event Date & Time</b></label>
        <input type="datetime-local" placeholder="Enter Date & Time" name="date" required>
          <br />
          <br />

        <label for="email"><b>Event Image</b></label>
        <input type="file" placeholder="Select Image (optional)" id="myFile" name="filename">
          <br />
          <br />

        <label for="email"><b>Event Information</b></label>
        <input type="text" placeholder="Enter Information" name="info" required>

        <button type="submit" class="btn">Add</button>
        <button type="button" class="btn cancel" onclick="closeForm()">Close</button>
      </form>
    </div>

    <script>
        function openForm()
        {
          document.getElementById("myForm").style.display = "block";
        }

        function closeForm()
        {
          document.getElementById("myForm").style.display = "none";
        }
    </script>

</asp:Content>
