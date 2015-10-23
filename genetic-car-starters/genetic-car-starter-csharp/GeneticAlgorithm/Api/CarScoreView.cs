namespace GeneticAlgorithm.Api
{
    public class CarScoreView
    {
        public CarView Car { get; set; }
        public float Score { get; set; }

        public override string ToString()
        {
            return "CarScoreView{" +
                    "car=" + Car +
                    ", score=" + Score +
                    '}';
        }

    }
}
