var Accordion = Grommet.Accordion;
var AccordionPanel = Grommet.AccordionPanel;
var App = Grommet.App;
var Box = Grommet.Box;
var Button = Grommet.Button;
var CheckBox = Grommet.CheckBox;
var Heading = Grommet.Heading;
var Headline = Grommet.Headline;
var Label = Grommet.Label;
var Paragraph = Grommet.Paragraph;
var Select = Grommet.Select;
var Search = Grommet.Search;
var Split = Grommet.Split;
var Tab = Grommet.Tab;
var Table = Grommet.Table;
var TableRow = Grommet.TableRow;
var Tabs = Grommet.Tabs;
var Topology = Grommet.Topology;
var GlobeIcon = Grommet.Icons.Base.Globe

// airport object
function Airport (id, name, region, country) {
	this.id = id;
	this.name = name;
  this.region =region;
	this.country = country;
}

// list of searchable airports
var airports = [];
airports.push(new Airport(1, "Hartsfield Jackson Atlanta International", "Not Known", "United States"));
airports.push(new Airport(2, "Beijing Capital International", "Beijing", "China"));
airports.push(new Airport(3, "Dubai International", "Not Known", "United Arab Emirates"));

class TripCo extends React.Component {
  
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

  render () {
    return (
      
        <Box id='screen' pad='medium'>
        <Headline align='center' size='medium'>TripCo Online</Headline>
        <Tabs>
          <Tab title="Plan">
            <Box id="PlanBox" full='false'>
              <App>
                <Box id='Search' margin='medium' full='true' pad='small'>
                  <MySearch myDataProp = {this.state.word} 
                    updateStateProp = {this.updateState}></MySearch>
                </Box>

                <Box id="TripPreview" align='center' full='true'>
                  <Table>
                    <thead><tr><th>
                      <Box align='left'>
                      id
                      <select value={"optionsState"}></select>
                      </Box>
                      </th><th>AirPort</th><th>region</th><th>country</th></tr></thead>
                    <tbody>
                      {this.state.data.map((one, i) => <MyTableRow key = {i} data = {one} />)}
                    </tbody>
                  </Table>
                </Box>          
              </App>
            </Box>
          </Tab>
          
          <Tab title="Itinerary">
            <Box id="mapBox" full='true' margin='large'>
            </Box>  
          </Tab>

          <Tab title="TravelMap">
            <Box id="mapBox" full='true' margin='large'>
            </Box>  
          </Tab>
        </Tabs>
        
        </Box>
      
    );
  }
};

class MySelect extends React.Component {
  render(){
    return (
      <option value="A">Apple</option>
      <option value="B">Banana</option>
      <option value="C">Cranberry</option>
    );
  }
}

// Add an table row for each entry
class MyTableRow extends React.Component {
   render() {
      return (
         <TableRow>
            <td><CheckBox/> {this.props.data.id}</td>
            <td>{this.props.data.name}</td>
            <td>{this.props.data.region}</td>
            <td>{this.props.data.country}</td>
         </TableRow>
      );
   }
}

// Search Function 
class MySearch extends React.Component {

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
            if (obj.name.includes(query) || 
                obj.country.includes(query) ||
                obj.region.includes(query)) {
                return obj;
			}
        });
        console.log("[Main] search box find value " + searchResults);
        this.props.updateStateProp(searchResults);
	}

    render() {
        return (
          <input onChange={this.checkInput} defaultValue="Search Airport"
            id="searchBox" ref="searchBox" 
            type="text"></input>
        );
    }
}

var element = document.getElementById('content');
ReactDOM.render(React.createElement(TripCo), element);