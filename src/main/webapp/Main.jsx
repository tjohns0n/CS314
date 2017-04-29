
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

// Main Class for the whole page
class Main extends React.Component {
   constructor(props) {
      super(props);
		
      this.state = {
         data: airports,
         word: "hello"
      }

      this.updateState = this.updateState.bind(this);
   }

   updateState(results) {
        console.log("[App] Table refreshes value " + results);
        this.setState({data: results});
   }

   render() {
      return (
         <div>
            <div>
                <Search myDataProp = {this.state.word} 
                updateStateProp = {this.updateState}></Search>
            </div>
            <Header/>
            <table>
               <tbody>
                  {this.state.data.map((one, i) => <TableRow key = {i} data = {one} />)}
               </tbody>
            </table>
         </div>
      );
   }
}

// TODO
class Header extends React.Component {
   render() {
      return (
         <div>
            <h1>Header</h1>
         </div>
      );
   }
}

// Add an table row for each entry
class TableRow extends React.Component {
   render() {
      return (
         <tr>
            <td>{this.props.data.id}</td>
            <td>{this.props.data.name}</td>
            <td>{this.props.data.country}</td>
         </tr>
      );
   }
}

// Search Function 
class Search extends React.Component {

    constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.checkInput = this.checkInput.bind(this);
	}

    // if enter is pressed on the keyboard while typing:
    checkInput(event) {
		this.handleSubmit();
	}

    // when search is pressed or enter key is typed
    handleSubmit() {
		var query = this.refs.searchBox.value;
		console.log("[Main] search box get value : " + query);
		this.queryHandler(query);
	}

    // Check the airport names and countries to see if there are matches 
	// Push them to the searchResults 
	queryHandler(query) {
        console.log("[Main] Airports value " + airports);
        var searchResults = airports.filter(function(obj) { 
            if (obj.name.includes(query) || obj.country.includes(query)) {
                return obj;
			}
        });
        console.log("[Main] search box find value " + searchResults);
        this.props.updateStateProp(searchResults);
	}

    render() {
        return (
            <div>
            <input onChange={this.checkInput} id="searchBox" ref="searchBox" type="text"></input>
            </div>
        );
    }
}

ReactDOM.render(<Main />, document.getElementById("searchTest"));