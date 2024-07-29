$(document).ready(function () {
    let imageCount = 62;
    let imageDir = "images";
    let imageExt = "jpg";
    let currentImage = 0;
    let interval = null;
    let isDragging = false;
    let startX = 0;

    // Preload images into an array
    function preloadImages() {
        for (let i = 1; i < imageCount; i++) {
            let number = i < 10 ? "0" + i : i;
            let img = new Image();
            img.src = imageDir + "/frame_" + number + "." + imageExt;
        }
    }

    // Update the background image of the 360 view
    function updateImage(index) {
        let number = index < 10 ? "0" + index : index;
        let imageUrl = imageDir + "/frame_" + number + "." + imageExt;
        $("#360-view").css("background-image", "url(" + imageUrl + ")");
    }

    // Mouse events for dragging
    $("#360-view").mousedown(function (event) {
        isDragging = true;
        startX = event.pageX;
        $(this).css("cursor", "grabbing");
    });

    $(document).mouseup(function () {
        if (isDragging) {
            isDragging = false;
            $("#360-view").css("cursor", "grab");
        }
    });

    $(document).mousemove(function (event) {
        if (isDragging) {
            let dx = startX - event.pageX;
            if (dx > 5) {
                currentImage = (currentImage + 1) % imageCount;
                updateImage(currentImage);
                startX = event.pageX;
            } else if (dx < -5) {
                currentImage = (currentImage - 1 + imageCount) % imageCount;
                updateImage(currentImage);
                startX = event.pageX;
            }
        }
    });

    // Actions when the modal is shown
    $("#view360Modal").on('shown.bs.modal', function () {
        preloadImages();
        updateImage(0);
    });

    // Actions when the modal is hidden
    $("#view360Modal").on('hidden.bs.modal', function () {
        $("#360-view").css("background-image", "");
    });
});
