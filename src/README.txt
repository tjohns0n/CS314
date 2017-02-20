TripCo is a trip planning program that creates the shortest trip from a given list of locations
TripCo takes an (absolute if not in directory diretly above src) file path to a .csv file of longitude and lattitude coordinates to construct a trip from
.csv Location files should have the first line as a template line with labels for subsequent lines' data
Optional arguments:
	-i : shows the ID of the locations on the map
	-m : Display mileage of legs on map
	-n : shows the names of the locations on the map

EX: TripCo -mn list.csv
