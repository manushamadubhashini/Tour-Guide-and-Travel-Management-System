document.addEventListener('DOMContentLoaded', function() {
  const packagesContainer = document.getElementById('packagesContainer');
  const API_BASE_URL = 'http://localhost:8080/api/v1';

  function generateStars(rating) {
    const fullStars = Math.floor(rating);
    const halfStar = rating % 1 !== 0;
    let starsHTML = '';

    for (let i = 0; i < fullStars; i++) {
      starsHTML += '<small class="fa fa-star text-primary"></small>';
    }

    if (halfStar) {
      starsHTML += '<small class="fa fa-star-half text-primary"></small>';
    }

    return starsHTML;
  }

  fetch(`${API_BASE_URL}/tour`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(response => {
      const packages = response.data || [];
      packagesContainer.innerHTML = ''; // Clear loading spinner

      packages.forEach(pkg => {
        const packageCard = document.createElement('div');
        packageCard.className = 'col-lg-4 col-md-6 wow fadeInUp';
        packageCard.innerHTML = `
          <div class="package-item">
            <div class="overflow-hidden">
              <img class="img-fluid" src="data:image/jpeg;base64,${pkg.image}" alt="${pkg.name}">
            </div>
            <div class="d-flex border-bottom">
              <small class="flex-fill text-center border-end py-2">
                <i class="fa fa-map-marker-alt text-primary me-2"></i>${pkg.name}
              </small>
              <small class="flex-fill text-center border-end py-2">
                <i class="fa fa-calendar-alt text-primary me-2"></i>${pkg.duration} days
              </small>
              <small class="flex-fill text-center py-2">
                <i class="fa fa-user text-primary me-2"></i>Group
              </small>
            </div>
            <div class="text-center p-4">
              <h3 class="mb-0">$${pkg.price}</h3>
              <div class="mb-3">
                ${generateStars(4.5)}
              </div>
              <p>${pkg.description}</p>
              <div class="d-flex justify-content-center mb-2">
                <a href="#" data-id="${pkg.id}" class="btn btn-sm btn-primary px-3 border-end read-more" style="border-radius: 30px 0 0 30px;">
                  Read More
                </a>
                <a href="#" data-id="${pkg.id}" class="btn btn-sm btn-primary px-3 book-now" style="border-radius: 0 30px 30px 0;">
                  Book Now
                </a>
              </div>
            </div>
          </div>
        `;

        packagesContainer.appendChild(packageCard);

        // Book Now button event listener
        packageCard.querySelector('.read-more').addEventListener('click', function(e) {
          e.preventDefault();
          const tourId = this.getAttribute('data-id');
          console.log('Booking Tour ID:', tourId);

          // Directly use the tour ID without additional fetching
          if (tourId) {
            localStorage.setItem('selectedTourId', tourId);
            window.location.href = `test2.html?id=${tourId}`;
          } else {
            alert('Unable to process booking. Invalid tour ID.');
          }
        });

        // // Read More button event listener
        // packageCard.querySelector('.book-now').addEventListener('click', function(e) {
        //   e.preventDefault();
        //   const tourId = this.getAttribute('data-id');
        //
        //   fetch(`${API_BASE_URL}/tour/${tourId}`)
        //     .then(response => {
        //       if (!response.ok) {
        //         throw new Error('Network response was not ok');
        //       }
        //       return response.json();
        //     })
        //     .then(tourResponse => {
        //       const tour = tourResponse.data || tourResponse;
        //       console.log('Tour Details:', tour);
        //
        //       if (tour && tour.id) {
        //         localStorage.setItem('selectedTour', JSON.stringify(tour));
        //         window.location.href = `booking.html?id=${tour.id}`;
        //       } else {
        //         throw new Error('Invalid tour data received');
        //       }
        //     })
        //     .catch(error => {
        //       console.error('Error fetching tour details:', error);
        //       alert('Unable to fetch tour details. Please try again.');
        //     });
        // });
        // Modify your Book Now button event listener
        packageCard.querySelector('.book-now').addEventListener('click', function(e) {
          e.preventDefault();
          const tourId = this.getAttribute('data-id');

          fetch(`${API_BASE_URL}/tour/${tourId}`)
            .then(response => {
              if (!response.ok) {
                throw new Error('Network response was not ok');
              }
              return response.json();
            })
            .then(tourResponse => {
              const tour = tourResponse.data || tourResponse;
              console.log('Tour Details:', tour);

              if (tour && tour.id) {
                // Store tour data in localStorage for checkout page
                localStorage.setItem('selectedTour', JSON.stringify(tour));
                // Redirect to checkout page
                window.location.href = `checkout.html?id=${tour.id}`;
              } else {
                throw new Error('Invalid tour data received');
              }
            })
            .catch(error => {
              console.error('Error fetching tour details:', error);
              alert('Unable to fetch tour details. Please try again.');
            });
        });
      });
    })
    .catch(error => {
      console.error('Detailed error:', error);
      packagesContainer.innerHTML = `
        <div class="alert alert-danger text-center">
          Unable to load packages. Error: ${error.message}
        </div>
      `;
    });


});
