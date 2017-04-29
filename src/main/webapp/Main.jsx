var App = Grommet.App;
var Box = Grommet.Box;
var Button = Grommet.Button;
var Heading = Grommet.Heading;
var Paragraph = Grommet.Paragraph;
var Search = Grommet.Search;

// airport object
function Airport (id, name, country) {
	this.id = id;
	this.name = name;
	this.country = country;
}

// list of searchable airports
var airports = [];
airports.push(new Airport(1, "Hartsfield Jackson Atlanta International", "United States"));
airports.push(new Airport(2, "Beijing Capital International", "China"));
airports.push(new Airport(3, "Dubai International", "United Arab Emirates"));

// store search results
var searchResults = [];

// Results class returns the result set of a search
// It is correctly displaying the <Heading> but will not call printResults for some reason
class Results extends React.Component {
	
	constructor(props) {
		super(props);
		
		this.state = {
			results: props.results
		};
		
		this.printResults = this.printResults.bind(this);
	}
	
	printResults() {
		var resultString = JSON.stringify(this.results);
		console.log(resultString + " ok");
		return <p>resultString</p>;
	}
	
	render () {
	  
		return (
			<div>
			<Heading>Results</Heading>
			{this.printResults}
			</div>
		);
	}
};

class Main extends React.Component {
	
	constructor(props) {
		super(props);
		
		this.state = {
			getResults: null
		};
		
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleEnterPress = this.handleEnterPress.bind(this);
	}
	
	// when search is pressed or enter key is typed
	handleSubmit(event) {
		var query = this.refs.searchBox.value;
		this.refs.searchBox.value = "";
		console.log(query);
		this.queryHandler(query);
	}
	
	// if enter is pressed on the keyboard while typing:
	handleEnterPress(event) {
		if (event.key == "Enter") {
			this.handleSubmit(event);
			event.preventDefault();
		}
	}
	
	// Check the airport names and countries to see if there are matches 
	// Push them to the searchResults 
	queryHandler(query) {
		searchResults = [];
		for (var i = 0; i < airports.length; i++) {
			if (airports[i].name.includes(query) || airports[i].country.includes(query)) {
				console.log("success");
				searchResults.push(airports[i]);
			}
		}
		// Display search results
		this.setState ({
			getResults: <Results results={searchResults} />
		});
	}
	
	render () {
	  
		return (
			<App>
			<Heading align="center">Where do you want to go?</Heading>
			<Box>
			<input onKeyPress={this.handleEnterPress} id="searchBox" ref="searchBox" type="search"></input>
			<Button onClick={this.handleSubmit} label="Search"></Button>
			{this.state.getResults}
			</Box>
			</App>
		);
	}
};

ReactDOM.render(<Main/>, document.getElementById("searchTest"));