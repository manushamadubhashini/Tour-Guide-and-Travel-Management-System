
document.addEventListener('DOMContentLoaded', function() {

  const fetchWeatherBtn = document.getElementById('fetchWeatherBtn');
  const weatherWidget = document.getElementById('weatherWidget');


  fetchWeatherBtn.addEventListener('click', fetchWeatherData);

  function fetchWeatherData() {
    const location = document.getElementById('destinationLocation').value;

    if (!location) {
      alert('Please enter a location first');
      return;
    }


    fetchWeatherBtn.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Loading...';
    fetchWeatherBtn.disabled = true;


    $.ajax({
      url: `http://localhost:8080/api/weather`,
      method: 'GET',
      data: { location: location },
      dataType: 'json',
      success: function(weatherData) {

        document.getElementById('currentTemp').textContent = weatherData.current.temp.toFixed(1);
        document.getElementById('currentCondition').textContent = weatherData.current.condition;

        // Format forecast description
        let forecastDesc = weatherData.forecast.map(day =>
          `${day.date.substring(0, 10)}: ${day.condition}, ${day.avgTemp.toFixed(1)}°C`
        ).join('; ');

        document.getElementById('forecastDesc').textContent = forecastDesc;

        // Suggest weather info text
        document.getElementById('weatherInfo').value =
          `${weatherData.current.condition} climate with average temperatures of ${weatherData.current.temp.toFixed(1)}°C. ` +
          `Humidity: ${weatherData.current.humidity}%, Wind: ${weatherData.current.wind.toFixed(1)} m/s. ` +
          `Forecast shows varied conditions over the next 5 days.`;

        // Show weather widget
        weatherWidget.style.display = 'block';

        // If you have a function to suggest best time to visit, uncomment this
        // suggestBestTimeToVisit(location, weatherData);
      },
      error: function(error) {
        console.error('Error fetching weather:', error);
        alert('Failed to fetch weather information. Please try again or enter a different location.');
      },
      complete: function() {
        // Reset button
        fetchWeatherBtn.innerHTML = 'Fetch Weather Info';
        fetchWeatherBtn.disabled = false;
      }
    });
  }
});

function loadDestinations() {
  const destinationsList = document.getElementById('destinationsList');

  // Show loading state
  destinationsList.innerHTML = '<div class="text-center"><div class="spinner-border" role="status"><span class="visually-hidden">Loading...</span></div></div>';

  $.ajax({
    url:"http://localhost:8080/api/v1/destination",
    method:"GET",
    dataType: "json",
    success:(res)=> {
      const destinations = res.data;

      if (destinations.length === 0) {
        destinationsList.innerHTML = '<div class="alert alert-info">No destinations found.</div>';
        return;
      }

      // Generate HTML for destinations
      let html = '';
      destinations.forEach(destination => {
        html += `
            <div class="card destination-card">
              <div class="row g-0">
                <div class="col-md-5">
                  ${destination.image ?
          `<img src="data:image/jpeg;base64,${destination.image}" class="img-fluid rounded-start" alt="${destination.name}" style="height: 300px;object-fit: cover;width: 105%!important;">` :
          `<div class="bg-light text-center p-5 h-200 d-flex align-items-center justify-content-center">
                      <span class="text-muted">No Image</span>
                    </div>`
        }
                </div>
                <div class="col-md-7">
                  <div class="card-body">
                    <h4 class="card-title">${destination.id}</h4>
                    <h5 class="card-title">${destination.name}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${destination.location}</h6>
                    <p class="card-text">${destination.description}</p>
                    <p class="card-text"><small class="text-muted">Best time to visit: ${destination.best_time_to_visit}</small></p>
                    <div class="btn-group">
                      <button type="button" class="btn btn-sm btn-outline-primary"
                       onclick="editDestination('${destination.id}', '${destination.name}', '${destination.description}', '${destination.location}', '${destination.weather_info}', '${destination.best_time_to_visit}', '${destination.image}')">Edit</button>
                      <button type="button" class="btn btn-sm btn-outline-danger" onclick="deleteDestination('${destination.id}')">Delete</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          `;
      });

      destinationsList.innerHTML = html;
    },

    error:(err)=> {
      console.error('Error loading destinations:', error);
      destinationsList.innerHTML = '<div class="alert alert-danger">Failed to load destinations.</div>';
    }
  });
}

loadDestinations();
// Add this to your existing script section
document.addEventListener('DOMContentLoaded', function() {
  // Get DOM elements
  const fetchWeatherBtn = document.getElementById('fetchWeatherBtn');
  const weatherWidget = document.getElementById('weatherWidget');
  const destinationForm = document.getElementById('destinationForm');
  const bestTimeSelect = document.getElementById('bestTimeToVisit');
  const customBestTime = document.getElementById('customBestTime');

  // Add event listener to the button
  fetchWeatherBtn.addEventListener('click', fetchWeatherData);

  // Show/hide custom best time field based on dropdown selection
  bestTimeSelect.addEventListener('change', function () {
    if (this.value === 'custom') {
      customBestTime.style.display = 'block';
      customBestTime.required = true;
    } else {
      customBestTime.style.display = 'none';
      customBestTime.required = false;
    }
  });
})
$('#btn-save').click((e)=>{
  e.preventDefault()
  // const id=$('#destinationId').val();
  // const name=$('#destinationName').val();
  // const description=$('#destinationDescription').val()
  // const location=$('#destinationLocation').val()
  // const weatherInfo=$('#weatherInfo').val()
  // const  bestTimeToVisit=$('#bestTimeToVisit').val()
  // const image=$('#destinationImage').val()
  //
  // const destinationData={
  //   id:id,
  //   name:name,
  //   description:description,
  //   location:location,
  //   weather_info:weatherInfo,
  //   best_time_to_visit:bestTimeToVisit,
  //   image
  // }
  const formData = new FormData();


  formData.append('id', $('#destinationId').val());
  formData.append('name', $('#destinationName').val());
  formData.append('description', $('#destinationDescription').val());
  formData.append('location', $('#destinationLocation').val());
  formData.append('weather_info', $('#weatherInfo').val());
  formData.append('best_time_to_visit', $('#bestTimeToVisit').val());


  const imageFile = $('#destinationImage')[0].files[0];
  formData.append('image', imageFile);

  $.ajax({
    url:"http://localhost:8080/api/v1/destination",
    method:"POST",
    data:formData,
    contentType: false,
    processData: false,
    success: (res) =>{
      console.log(res)
      loadDestinations()
    },
    error:(err) =>{
      console.log(err)
    }
  })
})
const deleteDestination=(id) =>{
  $.ajax({
    url:`http://localhost:8080/api/v1/destination/delete/${id}`,
    type:'DELETE',
    success:(res) =>{
      console.log(res)
      loadDestinations()
    },
    error:(err) =>{
      console.log(err)
    }

  })

}
const editDestination=(id,name,description,location,weather_info,best_time_to_visit,image)=>{

  $('#destinationId').val(id);
  $('#destinationName').val(name);
  $('#destinationDescription').val(description);
  $('#destinationLocation').val(location);
  $('#weatherInfo').val(weather_info);
  $('#bestTimeToVisit').val(best_time_to_visit);
  document.getElementById('btn-save').textContent = 'Update Destination';



}
$('#btn-save').click((e) =>{
  e.preventDefault()
  const formData = new FormData();

  formData.append('id', $('#destinationId').val());
  formData.append('name', $('#destinationName').val());
  formData.append('description', $('#destinationDescription').val());
  formData.append('location', $('#destinationLocation').val());
  formData.append('weather_info', $('#weatherInfo').val());
  formData.append('best_time_to_visit', $('#bestTimeToVisit').val());

  $.ajax({
    url:'http://localhost:8080/api/v1/destination',
    type:'PUT',
    data:formData,
    success:(res)=>{
      console.log(res)
      loadDestinations()
    },
    error:(err)=>{
      console.log(err)
    }

  })
})
