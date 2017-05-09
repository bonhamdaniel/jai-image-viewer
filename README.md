# jai-image-viewer
Image viewer implemented using the Java Advanced Imaging (JAI) API.

This program requires installation of the JAI API, which can be found at:
http://www.oracle.com/technetwork/java/iio-141084.html

Following installation of the API, it's JARs must be added to the build path.  If using Eclipse, this can be accomplished via the following steps:
1. Menu: Project -> Properties
2. Select “Java Build Path” from the left hand menu
3. Select the Libraries tab
4. Click the “Add External JARs…” button
5. Find your “Java Advanced Imaging 1.1.3” folder, previously installed
6. Navigate to the “lib” folder
7. Add the “jai_codec”, “jai_core”, and “mlibwrapper_jai” JARs to the project build path.

Usage:
o	A default image is provided
o	The “Zoom In”, “Zoom Out”, and “Reset Image” buttons can be used to resize the image, as desired.  The image is allowed to be zoomed in   10 times, and can be zoomed back out until it reaches its original size.
o	Scrollbars are provided to navigate to sections of the image that are not within the viewing region.
o	There is a “File” menu provided, from which an “Open File” menu item can be chosen to load a new image in the Image Viewer.
o	There is a “File” menu provided, from which an “Exit” menu item can be chosen to exit the program.
o	The window can be resized; the image will remain in the upper left corner of the viewer.
