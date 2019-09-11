Feature: Apis for data creation and consultation

  Scenario: A politicians can be created using the API
    Given the politician with below data
      | Ronal Regan | USA | 1911 | President | 3 |
    When the data are submitted using the CREATE Api
    Then the politician is added to the database
    And is available by id

# Below is an assumption of what are the invalid data
# More information from the developers are required 
  Scenario: Submitting a new politicia with invalid data
    Given the politician with below data
      | Mickey Mouse | 0 | ABC | N/A | 9 |
    When the data are submitted using the CREATE Api
    Then the politician is not added to the databse
    
    
  Scenario: A politician is created and then is visualised inside the latest 5 politicians
  	Given the politician with below data
      | Ronal Regan | USA | 1911 | President | 3 |
    When the data are submitted using the CREATE Api
    Then the politician is added to the database
    And they are available insidde the five latest politicians
    
 		Scenario: No politician is retrieved if not passing an id
 		Given the politician API
 		When the retrieve politician API is called without an id
 		Then no politician is returned
 		And the status code returned should be 404
