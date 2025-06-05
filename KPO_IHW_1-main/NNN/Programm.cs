using System;
using System.Collections.Generic;
using NNN;
using ClassLibrary1;
using System.Data;

class Programm
{
    public static void Main()
    {
        UserMenu menu = new UserMenu();
        do
        {
            Console.Clear(); 
            try
            {
                menu.StartProgramm();
                Console.Clear();
                List<GameProfile> dataInFile = OpenFileAndSetDates.SetDatesInCollection();
                do
                {
                    Console.Clear();
                    menu = new UserMenu(UserAllMenuCells.usersStartChoice, UserAllMenuCells.firstAction);
                    int selectedIndex = menu.Run();
                    Console.ResetColor();
                    List<GameProfile> datesAfterAction = OptionChoice.ChoiceAction(selectedIndex, dataInFile);
                    Functions.ViewDates(datesAfterAction);
                    ConsoleLogger.ColorByLog(LogLevel.Info, "Press [Enter] For start choice again and press other button for exit.");
                } while (Console.ReadKey().Key != ConsoleKey.Escape);

            }
            catch (Exception e)
            {
                ConsoleLogger.ColorByLog(LogLevel.Errored, e.Message);
            }
        } while (Console.ReadKey().Key != ConsoleKey.Escape);
        ConsoleLogger.ColorByLog(LogLevel.Warning, UserAllMenuCells.close);
    }

    
}