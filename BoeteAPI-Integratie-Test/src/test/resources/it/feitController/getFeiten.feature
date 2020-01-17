# language: nl

Functionaliteit: IT-GetFeiten

  Achtergrond:
    Gegeven er zitten 3 feiten in de database.

    Scenario: Vraag alle feiten op via endpoint
      Als de client vraagt alle feiten op via "/feiten"
      Dan moet de HTTP status code 200 de Response dient 3 feiten te bevatten