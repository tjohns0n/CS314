DTR-14

Overview:
        TripCo is a trip planning program that creates a short trip 
        from a given list of locations. TripCo takes a file path (relative to 
        the folder above src/ or an absolute path) to a .csv file of longitude 
        and latitude coordinates to construct a trip from. 

        .csv Location files should have the first line as a template line 
        with labels for subsequent lines' data. 

Optional arguments:
            -i : shows the ID of the locations on the map
            -m : Display mileage of legs on map
            -n : shows the names of the locations on the map
        -i and -n cannot be used in conjunction. 
        EX: TripCo -mn list.csv

Output:
        (csv root name).xml - an XML itinerary of the trip, 
        including the sequence, start location, end location, 
        and mileage of each leg of the trip
        
        (csv root name).svg - an SVG of the trip, drawn over 
        a map of Colorado. If options are specified, text labels
        for each one will be added to the map

        View.html - A web page of the trip will automatically be 
        opened displaying the SVG and a link to the raw XML data

Purpose:
        The first release introduces many features. The main feature
        of the TripCo program is the ability to read in a list of 
        locations and produce a short trip between all of them. 
        This trip is then displayed (locally) in a web browser. 
        
Issues:
        - The current release only supports the web presentation
        locally. Future releases will allow client-server interaction.
        
        - There is no way to select individual locations
        from a .csv, and only a single .csv is accepted

        - The SVG and web page are fairly basically formatted

        - The unit tests are not as robust as they could be, and
        there is no master test runner

Notes:
        - Assistance for great circle distance from:
            http://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
        - Understanding of nearest neighbor:
            https://en.wikipedia.org/wiki/Nearest_neighbor_search
        - ReactJS and Grommet were used to build the web page:
            https://facebook.github.io/react/
            https://grommet.github.io/
