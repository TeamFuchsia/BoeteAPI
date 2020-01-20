# language: nl

Functionaliteit: IT-GetFeiten

  Achtergrond:
    Gegeven er zitten 3 feiten t.b.v. het updaten van een feit in de database.

    Als de client een PUT updateFeitById met een "goede" invoer maakt naar "/feiten/"
    Dan moet de HTTP status code 200 zijn en moet in de response o.a de gewijzigde feitcode "VBF-200" zitten