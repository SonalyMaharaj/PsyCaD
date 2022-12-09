<%@ Page Title="" Language="C#" MasterPageFile="~/MindOverMatter.Master" AutoEventWireup="true" CodeBehind="Profile.aspx.cs" Inherits="MoM_Web.Profile" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <style>
        .card 
        {
            /* Add shadows to create the "card" effect */
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            width: 875px;
            height:150px;
            border-radius:20px;
            text-align:center;
            background: transparent;
        }

        /* On mouse-over, add a deeper shadow */
        .card:hover 
        {
            box-shadow: 2px 8px 16px 2px #f6660a;
        }

        .selectt 
        {
            color: #fff;
            padding: 30px;
            display: none;
            margin-top: 30px;
            width: 60%;
            background: green;
        }
          
        label 
        {
            margin-right: 20px;
        }

    </style>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <h2 class="mb-4" style="color:#5023af; text-align:center;">Profile of Student</h2>

    <div class="container">
        <div class="card">
            <div class="row">
                <div class="col-sm-6">
                    <img src="images/avatar.png" alt="Avatar" style="width:100px;height:70px;padding:5px;">
                </div>
                <div class="col-sm-6">
                    <h2 style="color:#5023af;"><b>Jane Doe</b></h2>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <p style="font-size:10px">217031068 <br />ID Number: 9903010181085<br />Email: janedoe@gmail.com<br />Contact Number: 0725669054</p>
                </div>
                <div class="col-sm-6">
                    <p style="font-size:10px">Campus: APK<br />Faculty: Science<br />File Number: xxxxxxxxxx<br />Gender: Female</p>
                </div>
            </div>
        </div>
    </div>


    <center>
        <br />
        <h5> Please select a table to view: </h5>
        <div>
            <label>
                <input type="radio" name="colorRadio" value="Appointment"> Appointments
            </label>
            <label>
                <input type="radio" name="colorRadio" value="Diary"> Diary Entries
            </label>
            <label>
                <input type="radio" name="colorRadio" value="Mood"> Mood Tracker
            </label>
            <label>
                <input type="radio" name="colorRadio" value="Call"> Call Logs
            </label>
        </div>

        <div class="Appointment selectt">
          table1
        </div>
        <div class="Diary selectt">
          table2
        </div>
        <div class="Mood selectt">
          table3
        </div>
        <div class="Call selectt">
          table4
        </div>


        <div>
        <table id="tableid">
            <thead>
            <tr>
            <th>Student Number</th>
            <th>Student Name</th>
            <th>Student Surname</th>
            <th>Student Email</th>
            <th>Student ID Number</th>
            <th>Remove User</th>
            </tr>
            </thead>
            <tbody onclick="rowClick();">
            <tr>
            <td>217031068</td>
            <td>Sonaly</td>
            <td>Maharaj</td>
            <td>sonalymaharaj01@gmail.com</td>
            <td>9903010181085</td>
            <th><button type="button" style="background-color:transparent;border-width:1px;border-radius:10px;"><i class="fa fa-trash-o" aria-hidden="true"></i></button></th>
            </tr>
            <tr>
            <td>220070869</td>
            <td>Solomon</td>
            <td>Moyo</td>
            <td>peacefulmoyo7@gmail.com</td>
            <td>0204156473082</td>
            <th><i class="fa fa-trash-o" aria-hidden="true"></i></th>
            </tr>
            </tbody>
        </table>
    </div>


        <script type="text/javascript">
            $(document).ready(function ()
            {
                $('input[type="radio"]').click(function ()
                {
                    var inputValue = $(this).attr("value");
                    var targetBox = $("." + inputValue);
                    $(".selectt").not(targetBox).hide();
                    $(targetBox).show();
                });
            });
        </script>
    </center>


</asp:Content>
