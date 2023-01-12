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
            background: transparent;
        }
          
        label 
        {
            margin-right: 20px;
        }

        table 
        {
          border-collapse: collapse;
          width: 100%;
          margin-left: auto;
          margin-right: auto;
          color:black;
          font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
        }

        td, th 
        {
          border: 1px solid #dddddd;
          text-align: center;
          padding: 10px;
        }
        
        tbody tr:hover 
        {
            cursor:pointer;
            background-color: #f6660a;
        }
    </style>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <h2 class="mb-4" style="color:#5023af; text-align:center;">Profile of Student</h2>

    <center>
        <div class="container">
            <div class="card">
                <div class="row"> 
                    <div class="col-sm-6" id="ProfileLeft" runat="server">
                       <!--From Code Behind-->
                  
                    </div>
                    <div class="col-sm-6" id="ProfileRight" runat="server">
                        <h2 style="color:#5023af;"><b>Jane Doe</b></h2>
                         <p style="font-size:10px">Campus: APK<br />Faculty: Science<br />File Number: xxxxxxxxxx<br />Gender: Female</p>
                    </div>
                </div>
              
            </div>
        </div>
    </center>


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
            <div>
                <table id="tblappointment">
                    <thead>
                        <tr>
                            <th>Date and Time</th>
                            <th>Professional</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>01 May 2022 1pm</td>
                            <td>Dr Du Toit</td>
                        </tr>
                        <tr>
                            <td>28 January 2022 10am</td>
                            <td>Dr Bree</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="Diary selectt">
            <div>
                <table id="tbldiary">
                    <thead>
                        <tr>
                            <th>Date and Time</th>
                            <th>Flag</th>
                            <th>Attended To</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>01 May 2022 1pm</td>
                            <td>Suicide</td>
                            <td><input type="checkbox" class="checkstyle" name="attend"/></td>
                        </tr>
                        <tr>
                            <td>28 January 2022 10am</td>
                            <td>Death</td>
                            <td><input type="checkbox" class="checkstyle" name="attend"/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="Mood selectt">
            <div>
                <table id="tblmood">
                    <thead>
                        <tr>
                            <th>Date and Time</th>
                            <th>Mood</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>01 May 2022 1pm</td>
                            <td>Happy</td>
                        </tr>
                        <tr>
                            <td>28 January 2022 10am</td>
                            <td>Sad</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="Call selectt">
            <div>
                <table id="tblcall">
                    <thead>
                        <tr>
                            <th>Date and Time</th>
                            <th>Line</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>01 May 2022 1pm</td>
                            <td>Crisis Line</td>
                        </tr>
                        <tr>
                            <td>28 January 2022 10am</td>
                            <td>Help Desk</td>
                        </tr>
                    </tbody>
                </table>
            </div>
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
