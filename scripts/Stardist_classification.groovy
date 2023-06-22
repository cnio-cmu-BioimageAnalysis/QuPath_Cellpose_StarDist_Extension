import qupath.ext.stardist.StarDist2D

// Define model and resolution
var pathModel = "/path/to/classification/model.pb"
double pixelSize = 0.5

// Define a classification map, connecting prediction labels and classification names
var classifications = [
    0: 'Background',
    1: 'Stroma',
    2: 'Tumor'
]

var stardist = StarDist2D.builder(pathModel)
        .threshold(0.5)
        .simplify(0)
        .classificationNames(classifications) // Include names so that classifications can be applied
        .keepClassifiedBackground(false)      // Optionally keep detections that are classified as background (default is false)
        .normalizePercentiles(1, 99)
        .pixelSize(pixelSize)
        .build()

// Run detection for the selected objects
var imageData = getCurrentImageData()
var pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}
stardist.detectObjects(imageData, pathObjects)
println 'Done!'