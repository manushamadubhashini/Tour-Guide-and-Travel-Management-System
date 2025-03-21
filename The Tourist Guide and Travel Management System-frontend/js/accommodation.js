function loadAccommodation() {
  const accommodationList = document.getElementById('accommodationList');

  accommodationList.innerHTML = '<div class="text-center"><div class="spinner-border" role="status"><span class="visually-hidden">Loading...</span></div></div>';

  $.ajax({
    url: "http://localhost:8080/api/v1/accommodation",
    method: "GET",
    dataType: "json",
    success: (res) => {
      const accommodations = res.data;
      if (accommodations.length === 0) {
        accommodationList.innerHTML = '<div class="alert alert-info">No accommodation found.</div>';
        return;
      }

      let html = '';
      accommodations.forEach(accommodations => {
        html += `
            <div class="card accommodation-card">
              <div class="row g-0">
                <div class="col-md-5">
                  ${accommodations.image ?
          `<img src="data:image/jpeg;base64,${accommodations.image}" class="img-fluid rounded-start" alt="${accommodations.name}" style="height: 300px;object-fit: cover;width: 105%!important;">` :
          `<div class="bg-light text-center p-5 h-200 d-flex align-items-center justify-content-center">
                      <span class="text-muted">No Image</span>
                    </div>`
        }
                </div>
                <div class="col-md-7">
                  <div class="card-body">
                    <h3 class="card-title">${accommodations.id}</h3>
                    <p class="card-title">Destination Id:${accommodations.destinationId}</p>
                    <h4 class="card-title">${accommodations.name}</h4>
                    <h6 class="card-subtitle mb-2 text-muted" style="color: #0d6efd!important;">${accommodations.description}</h6>
                    <p class="card-text"><small class="text-muted">Accommodation Type: ${accommodations.type}</small></p>
                     <h6 class="card-subtitle mb-2 text-muted">RS.${accommodations.price}</h6>
                    <div class="btn-group">
                      <button type="button" class="btn btn-sm btn-outline-primary"
                       onclick="editAccommodation('${accommodations.id}','${accommodations.destinationId}' ,'${accommodations.name}', '${accommodations.description}', '${accommodations.type}','${accommodations.address}', '${accommodations.price}', '${accommodations.image}')">Edit</button>
                      <button type="button" class="btn btn-sm btn-outline-danger" onclick="deleteAccommodation('${accommodations.id}')">Delete</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          `;
      });

      accommodationList.innerHTML = html;
    },

    error: (err) => {
      console.error('Error loading accommodation:', err);
      accommodationList.innerHTML = '<div class="alert alert-danger">Failed to load accommodation.</div>';
    }
  });
}
function setAccommodationType(type) {
  $("#accommodationType").val(type);
}
loadAccommodation()
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

function editAccommodation(id, destinationId, name, description, type,address, price, image) {
  $("#accommodationId").val(id)
  $("#destinationId").val(destinationId)
  $("#accommodationName").val(name)
  $("#accommodationDescription").val(description)
  $("#accommodationType").val(type)
  $("#accommodationAddress").val(address)
  $("#accommodationPrice").val(price)
  $("#btn-save").text("Update Accommodation");
}
const deleteAccommodation=(id) => {
  $.ajax({
    url: `http://localhost:8080/api/v1/accommodation/delete/${id}`,
    method: "DELETE",
    success: (res) => {
      loadAccommodation()
      console.log(res)
    },
    error: (err) => {
      console.log(err)
    }
  })
}
$('#btn-save').click((e)=>{
  e.preventDefault()
  const formData = new FormData();
  formData.append("id", $("#accommodationId").val());
  formData.append("destinationId", $("#destinationId").val());
  formData.append("name", $("#accommodationName").val());
  formData.append("description", $("#accommodationDescription").val());
  formData.append("type", $("#accommodationType").val());
  formData.append("address", $("#accommodationAddress").val());
  formData.append("price", $("#accommodationPrice").val());

  const imageFile = $('#accommodationImage')[0].files[0];
  formData.append('image', imageFile);

  $.ajax({
    url:"http://localhost:8080/api/v1/accommodation",
    method:"POST",
    data:formData,
    contentType: false,
    processData: false,
    success: (res) =>{
      loadAccommodation()
      console.log(res)
    },
    error:(err) =>{
      console.log(err)
    }
  })
})
