using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using GeneticAlgorithm.Algo;
using GeneticAlgorithm.Api;
//using GeneticAlgorithm.Properties;
using GeneticAlgorithm.RestClient;

namespace GeneticAlgorithm
{
    class Program
    {
        private static GeneticAlgorithm.RestClient.RestClient _client;

        private static Team _maTeam = Team.RED; //to change with your actual team color

        static void Main(string[] args)
        {
            Console.WriteLine("starting");

            _client = new GeneticAlgorithm.RestClient.RestClient
            {
                URL = "http://genetic-car.herokuapp.com",
                MyTeam = _maTeam.ToString()
            };

            DoMyAlgo();

            Console.ReadLine();
        }

        private static void DoMyAlgo()
        {
            List<CarView> cars = new List<CarView>();
            for (var i = 0; i < 20; i++)
            {
                cars.Add(Car.Random().ToCarView());
            }

            IEnumerable<CarScoreView> carScores = Evaluate(cars);

            CarScoreView champion = carScores.OrderByDescending(c => c.Score).First();

            Console.WriteLine("Mon champion est {0}", champion);
        }

        private static IEnumerable<CarScoreView> Evaluate(IReadOnlyCollection<CarView> cars)
        {
            return _client.Submit(cars).Result;
        }
    }
}
