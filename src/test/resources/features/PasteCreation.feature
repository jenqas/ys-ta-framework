Feature: As a user I want to get ability to create Paste Bin

  Background:
    Given the user opens Paste Bin website
    And the user scrolls down homepage

  @smoke
  Scenario: Created paste bin details should consist of appropriate data
    When the user enters paste code "Hello from WebDriver"
    And the user enters paste expiration "10 Minutes"
    And the user enters paste name "helloweb"
    And the user clicks create button
    Then created paste is saved as Share button becomes visible