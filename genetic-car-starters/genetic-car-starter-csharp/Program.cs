using System;
using System.Collections.Generic;
using System.Linq;
using GeneticAlgorithm.Algo;
using GeneticAlgorithm.Api;

namespace GeneticAlgorithm
{
    class Program
    {
        // /!\ Change your team /!\
        private static Team _maTeam = Team.RED; //to change with your actual team color

        private static GeneticAlgorithm.RestClient.RestClient _client;

        static void Main(string[] args)
        {
            Console.WriteLine("starting");

            _client = new GeneticAlgorithm.RestClient.RestClient
            {
                URL = "https://gen-car-1.cleverapps.io",
                MyTeam = _maTeam.ToString()
            };

            DoMyAlgo();

        }

        private static void DoMyAlgo()
        {
            List<CarView> cars = new List<CarView>();
            for (var i = 0; i < 20; i++)
            {
                cars.Add(Car.Random().ToCarView());
            }

            IEnumerable<CarScoreView> carScores = Evaluate(cars);

            // Here comes your algo
            //******************** */

            //******************** */
            CarScoreView champion = carScores.OrderByDescending(c => c.Score).First();

            Console.WriteLine("Mon champion est {0}", champion);
        }

        private static IEnumerable<CarScoreView> Evaluate(IReadOnlyCollection<CarView> cars)
        {
            return (_client.Submit(cars).Result);
        }
    }
}
