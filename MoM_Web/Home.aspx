<%@ Page Title="" Language="C#" MasterPageFile="~/MindOverMatter.Master" AutoEventWireup="true" CodeBehind="Home.aspx.cs" Inherits="MoM_Web.Home" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

    <style>
        .searchbar
        {
            border-radius: 25px; 
            border-width: 1px; 
            width:300px; 
            height:35px;
            background-color:transparent;
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
          padding: 15px;
        }
        
        tbody tr:hover 
        {
            cursor:pointer;
            background-color: #f6660a;
        }

        tbody tr th:hover 
        {
            cursor:pointer;
            background-color: white;
        }
        
    </style>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <center><h2 class="mb-4" style="color:#5023af">Students</h2></center>

    <input class="searchbar" type="text" id="searchbox" onkeyup="performSearch()" placeholder=" Search table...">
    <br/>
    <br />

    <div style="max-height:400px; overflow-x:auto">
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
            <tbody id="tablebody" runat="server">
            </tbody>
        </table>
    </div>

    <script type="text/javascript">            
        function rowClick(StudentNumber)
        {
            location.href = "Profile.aspx?StudentNumber=" + StudentNumber; //when a row is clicked it will redirect to this address
        }

        function deleteClick()
        {
            
            location.href = "Appointments.aspx";
        }

        function performSearch() {

            // Declare search string 
            var filter = searchBox.value.toUpperCase();

            // Loop through first tbody's rows
            for (var rowI = 0; rowI < trs.length; rowI++) {

                // define the row's cells
                var tds = trs[rowI].getElementsByTagName("td");

                // hide the row
                trs[rowI].style.display = "none";

                // loop through row cells
                for (var cellI = 0; cellI < tds.length; cellI++) {

                    // if there's a match
                    if (tds[cellI].innerHTML.toUpperCase().indexOf(filter) > -1) {

                        // show the row
                        trs[rowI].style.display = "";

                        // skip to the next row
                        continue;

                    }
                }
            }

        }

        // declare elements
        const searchBox = document.getElementById('searchbox');
        const table = document.getElementById("tableid");
        const trs = table.tBodies[0].getElementsByTagName("tr");

        // add event listener to search box
        //searchBox.addEventListener('keyup', performSearch);
    </script>

</asp:Content>


