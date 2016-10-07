using System;
using System.Collections.Generic;
using System.Linq;
using GeneticAlgorithm.algo;
using GeneticAlgorithm.Api;
using GeneticAlgorithm.Properties;
using RestSharp;

namespace GeneticAlgorithm
{
    class Program
    {
        private static RestClient _client;

        private static Team _maTeam = Team.RED; //to change with your actual team color

        static void Main(string[] args)
        {
            Console.WriteLine("starting");

            _client = new RestClient
            {
                BaseUrl = new Uri(Settings.Default.host),
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
            var request = new RestRequest("simulation/evaluate/" + _maTeam, Method.POST) {RequestFormat = DataFormat.Json};
            request.AddBody(cars);
            
            return _client.Execute<List<CarScoreView>>(request).Data;
        }
    }
}
