using GeneticAlgorithm.Api;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.Serialization.Json;
using System.Threading.Tasks;
using System.IO;
using System.Text;
using Microsoft.Extensions.Logging;

namespace GeneticAlgorithm.RestClient
{
    public class RestClient {
        public string URL {get; set;}

        public string MyTeam {get; set;}

        public async Task<IEnumerable<CarScoreView>> Submit(IReadOnlyCollection<CarView> cars) {
            var client = new HttpClient();
            var carScoreViewSerializer = new DataContractJsonSerializer(typeof(List<CarScoreView>));
            var carViewSerializer = new DataContractJsonSerializer(typeof(List<CarView>));
            
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(
                new MediaTypeWithQualityHeaderValue("application/json"));
            MemoryStream stream1 = new MemoryStream();
            carViewSerializer.WriteObject(stream1, cars);
            var bodyContent = Encoding.UTF8.GetString(stream1.ToArray());
            var body = new StringContent(bodyContent);
            System.Diagnostics.Debug.WriteLine(bodyContent);
            client.DefaultRequestHeaders.Add("User-Agent", ".NET Foundation Repository Reporter");
            var postTask = client.PostAsync(URL + "/simulation/evaluate/" + MyTeam, body);
            var result = (await postTask).Content.ReadAsStreamAsync().Result;
            var memStream2 = new MemoryStream();
            result.CopyTo(memStream2);
            System.Diagnostics.Debug.WriteLine(Encoding.UTF8.GetString(memStream2.ToArray()));
            var carScoreViews = carScoreViewSerializer.ReadObject(result) as List<CarScoreView>;

            return carScoreViews;
        }
    }
            //    var request = new RestRequest("simulation/evaluate/" + _maTeam, Method.POST) {RequestFormat = DataFormat.Json};
            // request.AddBody(cars);
            // 
            // return _client.Execute<List<CarScoreView>>(request).Data;

}