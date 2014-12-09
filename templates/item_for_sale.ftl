<html>
<head>
    <title>View Item</title>
<#include "head.ftl">
</head>
<body>
	<h2>You are currently auctioning your ${item.name}</h2>
		
		<h4>${message}</h4>
	<br>
	<br>
	<form method="post" action="auction_item">
		<label>Please enter the opening price of the auction</label><input type="text" name="minPrice">
		<input type="hidden" value="${item.id}" name="item_id">
		
	<br>
	<fieldset class="date">
  <legend>End Date of Auction </legend>
  <label for="month_start">Month</label>
  <select id="month_start"
          name="month_start" />
    <option>January</option>      
    <option>February</option>      
    <option>March</option>      
    <option>April</option>      
    <option>May</option>      
    <option>June</option>      
    <option>July</option>      
    <option>August</option>      
    <option>September</option>      
    <option>October</option>      
    <option>November</option>      
    <option>December</option>      
  </select> -
  <label for="day_start">Day</label>
  <select id="day_start"
          name="day_start" />
    <option>1</option>      
    <option>2</option>      
    <option>3</option>      
    <option>4</option>      
    <option>5</option>      
    <option>6</option>      
    <option>7</option>      
    <option>8</option>      
    <option>9</option>      
    <option>10</option>      
    <option>11</option>      
    <option>12</option>      
    <option>13</option>      
    <option>14</option>      
    <option>15</option>      
    <option>16</option>      
    <option>17</option>      
    <option>18</option>      
    <option>19</option>      
    <option>20</option>      
    <option>21</option>      
    <option>22</option>      
    <option>23</option>      
    <option>24</option>      
    <option>25</option>      
    <option>26</option>      
    <option>27</option>      
    <option>28</option>      
    <option>29</option>      
    <option>30</option>      
    <option>31</option>      
  </select> -
  <label for="year_start">Year</label>
  <select id="year_start"
         name="year_start" />
       
    <option>2014</option>      
    <option>2015</option>      
    <option>2016</option>      
    <option>2017</option>      
    <option>2018</option>      
  </select>
  <span class="inst">(Month-Day-Year)</span>
</fieldset> 

<br>
<br>
<label>Select Ending Time</label>
    <select name="time" id="time">
    <option value="5:00 AM">5:00 AM</option>
    <option value="5:15 AM">5:15 AM</option>
    <option value="5:30 AM">5:30 AM</option>
    <option value="5:45 AM">5:45 AM</option>
     
    <option value="6:00 AM">6:00 AM</option>
    <option value="6:15 AM">6:15 AM</option>
    <option value="6:30 AM">6:30 AM</option>
    <option value="6:45 AM">6:45 AM</option>
     
    <option value="7:00 AM">7:00 AM</option>
    <option value="7:15 AM">7:15 AM</option>
    <option value="7:30 AM">7:30 AM</option>
    <option value="7:45 AM">7:45 AM</option>
     
    <option value="8:00 AM">8:00 AM</option>
    <option value="8:15 AM">8:15 AM</option>
    <option value="8:30 AM">8:30 AM</option>
    <option value="8:45 AM">8:45 AM</option>
     
    <option value="9:00 AM">9:00 AM</option>
    <option value="9:15 AM">9:15 AM</option>
    <option value="9:30 AM">9:30 AM</option>
    <option value="9:45 AM">9:45 AM</option>
     
    <option value="10:00 AM">10:00 AM</option>
    <option value="10:15 AM">10:15 AM</option>
    <option value="10:30 AM">10:30 AM</option>
    <option value="10:45 AM">10:45 AM</option>
     
    <option value="11:00 AM">11:00 AM</option>
    <option value="11:15 AM">11:15 AM</option>
    <option value="11:30 AM">11:30 AM</option>
    <option value="11:45 AM">11:45 AM</option>
     
    <option value="12:00 PM">12:00 PM</option>
    <option value="12:15 PM">12:15 PM</option>
    <option value="12:30 PM">12:30 PM</option>
    <option value="12:45 PM">12:45 PM</option>
     
    <option value="1:00 PM">1:00 PM</option>
    <option value="1:15 PM">1:15 PM</option>
    <option value="1:30 PM">1:30 PM</option>
    <option value="1:45 PM">1:45 PM</option>
     
    <option value="2:00 PM">2:00 PM</option>
    <option value="2:15 PM">2:15 PM</option>
    <option value="2:3 PM">2:30 PM</option>
    <option value="2:45 PM">2:45 PM</option>
     
    <option value="3:00 PM">3:00 PM</option>
    <option value="3:15 PM">3:15 PM</option>
    <option value="3:30 PM">3:30 PM</option>
    <option value="3:45 PM">3:45 PM</option>
     
    <option value="4:00 PM">4:00 PM</option>
    <option value="4:15 PM">4:15 PM</option>
    <option value="4:30 PM">4:30 PM</option>
    <option value="4:45 PM">4:45 PM</option>
     
    <option value="5:00 PM">5:00 PM</option>
    <option value="5:15 PM">5:15 PM</option>
    <option value="5:30 PM">5:30 PM</option>
    <option value="5:45 PM">5:45 PM</option>
     
    <option value="6:00 PM">6:00 PM</option>
    <option value="6:15 PM">6:15 PM</option>
    <option value="6:30 PM">6:30 PM</option>
    <option value="6:45 PM">6:45 PM</option>
     
    <option value="7:00 PM">7:00 PM</option>
    <option value="7:15 PM">7:15 PM</option>
    <option value="7:30 PM">7:30 PM</option>
    <option value="7:45 PM">7:45 PM</option>
     
    <option value="8:00 PM">8:00 PM</option>
    <option value=":15 PM">8:15 PM</option>
    <option value="8:30 PM">8:30 PM</option>
    <option value="8:45 PM">8:45 PM</option>
     
    <option value="9:00 PM">9:00 PM</option>
    <option value="9:15 PM">9:15 PM</option>
    <option value="9:30 PM">9:30 PM</option>
    <option value="9:45 PM">9:45 PM</option>
     
    <option value="10:00 PM">10:00 PM</option>
    <option value="10:15 PM">10:15 PM</option>
    <option value="10:30 PM">10:30 PM</option>
    <option value="10:45 PM">10:45 PM</option>
     
    <option value="11:00 PM">11:00 PM</option>
    <option value="11:15 PM">11:15 PM</option>
    <option value="11:30 PM">11:30 PM</option>
    <option value="11:45 PM">11:45 PM</option>
    </select>
    <br>
<input type="submit" value="Create Auction">
</form>

<br>
<br>
<form method="get" action="auction_item">
<input type="submit" value="Back to My Items">
</form>

</body>
</html>