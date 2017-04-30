var Accordion = Grommet.Accordion;
var AccordionPanel = Grommet.AccordionPanel;
var App = Grommet.App;
var Box = Grommet.Box;
var Button = Grommet.Button;
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

class TripCo extends React.Component {
  render () {
    return (
      
        <Box id='screen' pad='medium'>
        <Headline align='center' size='medium'>TripCo Online</Headline>
        <Tabs>
          <Tab title="Plan">
            <Box id="PlanBox" full='false'>
              <Split fixed={false} showOnResponsive='both'>
                <App>
                  <Box id='left' align='end'>
                  <Box id='Search' margin='medium' full='true' pad='small'>
                    <Search placeHolder='Search AirPort' inline={true} iconAlign='start'
                      onSelect={''}/>
                  </Box>
                  <Box id='Type' align='center' direction='row'>
                    <Label labelFor='Type'>{"Type"}</Label>
                    <Box algin='end' margin='medium'>
                      <Select id='SelectType' placeHolder='None' onChange={''} 
                      options={['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight']}/>
                    </Box>
                  </Box>
                  <Box id='Continent' align='center' direction='row' margin='none'>
                    <Label labelFor='SelectContinent'>{"Continent"}</Label>
                    <Box margin='medium'>
                      <Select id='SelectContinent' onChange={''} />
                    </Box>
                  </Box> 
                  <Box id='Country' align='center' direction='row' margin='none'>
                    <Label labelFor='SelectCountry'>{"Country"}</Label>
                    <Box margin='medium'>
                      <Select id='SelectCountry' onChange={''} />
                    </Box>
                  </Box> 
                  <Box id='Region' align='center' direction='row' margin='none'>
                    <Label labelFor='SelectRegion'>{"Region"}</Label>
                    <Box margin='medium'>
                      <Select id='SelectRegion' onChange={''} />
                    </Box>
                  </Box> 
                  </Box>
                </App>

                <Box align='center' pad='medium'> 
                  <Label>{"Choose Your AirPort"}</Label>
                  <Select placeHolder='None' inline={true} onChange={''} multiple={true}
                    options={['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight',
                              'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight',
                              'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight',
                              'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight'
                              ]}/>
                </Box>
              </Split>
            </Box>
          </Tab>

          <Tab title="TripPreview">
            <Box id="right" align='center' margin='large'>
              <Button icon={<GlobeIcon />} label='Plan Trip Now' onClick={''} plain={true} />
              <Box id="TripPreview" align='center' margin='large' size='large'>
                <Table>
                  <thead><tr><th>AirPort</th><th>City</th></tr></thead>
                  <tbody>
                    <TableRow>
                      <td>1</td><td>Alan</td>
                    </TableRow>
                    <TableRow>
                      <td>2</td><td>Chris</td>
                    </TableRow>
                    <TableRow>
                      <td>3</td><td>Eric</td>
                    </TableRow>
                    <TableRow>
                      <td>4</td><td>Tracy</td>
                    </TableRow>
                  </tbody>
                </Table>
              </Box>          
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

var element = document.getElementById('content');
ReactDOM.render(React.createElement(TripCo), element);