
// import "src/main/resources/static/css/calendar.css";
document.getElementById('calendar-app').innerHTML= `
<!-- Parent container for the calendar month-->
<div class="calendar-month">
    <!-- The calendar header-->
    <section class="calendar-month-header">
<!--    Month name-->
<div id="selected-month" class="calendar-month-header-selected-month">
July 2020
</div>

<!--Pagination-->
<div class="calendar-month-header-selectors">
<span id="previous-month-selector"><</span>
<span id="present-month-selector">Today</span>
<span id="next-month-selector">></span>
</div>
</section>

<!--Calendar grid header-->
    <ol id="days-of-week" class="day-of-week">
    <li>Mon</li>
    <li>Tue</li>
    <li>Wed</li>
    <li>Thu</li>
    <li>Fri</li>
    <li>Sat</li>
    <li>Sun</li>
    </ol>
    
<!--    Calendar grid-->
    <ol id="calendar-days" class="date-grid">
    <li class="calendar-day">
    <span>1</span>
    <span>2</span>
    <span>3</span>
    <span>4</span>
    <span>5</span>
    <span>6</span>
    <span>7</span>
    <span>8</span>
    <span>9</span>
    <span>10</span>
    <span>11</span>
    <span>11</span>
    <span>12</span>
    <span>13</span>
    <span>14</span>
    <span>15</span>
    <span>16</span>
    <span>17</span>
    <span>18</span>
    <span>19</span>
    <span>20</span>
    <span>21</span>
    <span>22</span>
    <span>23</span>
    <span>24</span>
    <span>25</span>
    <span>26</span>
    <span>27</span>
    <span>28</span>
    <span>29</span> <!-- example goes till 29? -->
<!--    <span>30</span>-->
<!--    <span>31</span>-->
    </li>
    </ol>
    </div>
    `;