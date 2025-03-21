function loadTour() {
  const tourList = document.getElementById('tourList');

  tourList.innerHTML = '<div class="text-center"><div class="spinner-border" role="status"><span class="visually-hidden">Loading...</span></div></div>';

  $.ajax({
    url: "http://localhost:8080/api/v1/tour",
    method: "GET",
    dataType: "json",
    success: (res) => {
      const tour = res.data;
      if (tour.length === 0) {
        tourList.innerHTML = '<div class="alert alert-info">No tour found.</div>';
        return;
      }

      let html = '';
      tour.forEach(tour => {
        html += `
            <div class="card tour-card">
              <div class="row g-0">
                <div class="col-md-5">
                  ${tour.image ?
          `<img src="data:image/jpeg;base64,${tour.image}" class="img-fluid rounded-start" alt="${tour.name}" style="height: 300px;object-fit: cover;width: 105%!important;">` :
          `<div class="bg-light text-center p-5 h-200 d-flex align-items-center justify-content-center">
                      <span class="text-muted">No Image</span>
                    </div>`
        }
                </div>
                <div class="col-md-7">
                  <div class="card-body">
                    <h3 class="card-title">${tour.id}</h3>
                    <p class="card-title">Destination Id:${tour.destinationId}</p>
                    <h4 class="card-title">${tour.name}</h4>
                    <h6 class="card-subtitle mb-2 text-muted" style="color: #0d6efd!important;">${tour.description}</h6>
                    <p class="card-text"><small class="text-muted">Duration Period: ${tour.duration}Days</small></p>
                     <h6 class="card-subtitle mb-2 text-muted">RS.${tour.price}</h6>
                    <div class="btn-group">
                      <button type="button" class="btn btn-sm btn-outline-primary"
                       onclick="editTour('${tour.id}','${tour.destinationId}' ,'${tour.name}', '${tour.description}', '${tour.duration}', '${tour.price}', '${tour.image}')">Edit</button>
                      <button type="button" class="btn btn-sm btn-outline-danger" onclick="deleteTour('${tour.id}')">Delete</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          `;
      });

      tourList.innerHTML = html;
    },

    error: (err) => {
      console.error('Error loading tour:', err);
      tourList.innerHTML = '<div class="alert alert-danger">Failed to load tour.</div>';
    }
  });
}
loadTour();
$("#btn-save").off("click").on("click", (e) => {
  e.preventDefault();

  const formData = new FormData();
  formData.append("id", $("#tourId").val()); // Required for update
  formData.append("destinationId", $("#destinationId").val());
  formData.append("name", $("#tourName").val());
  formData.append("description", $("#tourDescription").val());
  formData.append("duration", $("#tourDuration").val());
  formData.append("price", $("#tourPrice").val());

  const imageFile = $("#tourImage")[0].files[0];
  if (imageFile) {
    formData.append("image", imageFile);
  }

  const isUpdate = $("#tourId").val().trim() !== "";
  const method = isUpdate ? "PUT" : "POST";
  const url = "http://localhost:8080/api/v1/tour";

  $.ajax({
    url: url,
    method: method,
    data: formData,
    contentType: false,
    processData: false,
    success: (res) => {
      console.log("Success:", res);
      $("#tour-form")[0].reset();
      $("#tourId").val("");
      $("#btn-save").text("Save Tour");
      loadTour();
    },
    error: (err) => {
      console.error("Error:", err);
    },
  });
});
$("#load-destination-id").click((e)=>{
  e.preventDefault()
  $.ajax({
    url:"http://localhost:8080/api/v1/destination",
    method:"GET",
    success: (res) => {
      console.log(res)
      $("#destination-dropdown").empty()
      res.data.forEach(destination => {
        $("#destination-dropdown").append(`
        <li><a class="dropdown-item" href="#" onclick="setDestination('${destination.id}')">${destination.id}</a></li>
        `)
      })
    },
    error: (err) => {
      console.error(err);
    }
  })
})
function setDestination(destinationId){
  $("#destinationId").val(destinationId)
}
const editTour=(id,destinationId,name,description,duration,price,image) =>{
  $("#tourId").val(id)
  $("#destinationId").val(destinationId)
  $("#tourName").val(name)
  $("#tourDescription").val(description)
  $("#tourDuration").val(duration)
  $("#tourPrice").val(price)
  $("#btn-save").text("Update Tour");
}
const deleteTour=(id) =>{
  $.ajax({
    url:`http://localhost:8080/api/v1/tour/delete/${id}`,
    method:"DELETE",
    success:(res) =>{
      loadTour()
      console.log(res)
    },
    error:(err) =>{
      console.log(err)
    }
  })
}
