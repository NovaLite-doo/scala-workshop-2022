package finalTask

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import play.api.libs.json.Json
import tasks.finalTask.repository.categoryRepository.LocalJsonCategoryRepository
import tasks.finalTask.repository.videoRepository.LocalCsvVideoRepository
import tasks.finalTask.services.StatisticsServiceTask

class StatisticsServiceTaskSpec extends AsyncWordSpec with Matchers {

  val categoryRepository = new LocalJsonCategoryRepository(
    getClass.getClassLoader.getResourceAsStream("finalTask/categories.json")
  )
  val videoRepository = new LocalCsvVideoRepository(
    getClass.getClassLoader.getResourceAsStream("finalTask/videos.csv")
  )

  val statisticsService =
    new StatisticsServiceTask(categoryRepository, videoRepository)

  "StatisticsServiceTask" should {

    "find video with most views correctly" in {
      statisticsService.videoWithMostViews
        .map(_.map(_.title))
        .map(_.get mustEqual "ZAYN - Dusk Till Dawn ft. Sia")
    }

    "find video with least views correctly" in {
      statisticsService.videoWithLeastViews
        .map(_.map(_.title))
        .map(_.get mustEqual "Katy Perry: Will You Be My Witness?")
    }

    "find category with most views correctly" in {
      statisticsService.categoryWithMostViews
        .map(_.map(_.snippet.title))
        .map(_.get mustEqual "Entertainment")
    }

    "find category with least views correctly" in {
      statisticsService.categoryWithLeastViews
        .map(_.map(_.snippet.title))
        .map(_.get mustEqual "Shows")
    }

    "find channel with most views for each category correctly" in {
      val correctChannelsForCategory = Json
        .parse(
          getClass.getClassLoader.getResourceAsStream(
            "finalTask/correct-channels-per-category.json"
          )
        )
        .as[Map[String, String]]
        .map { case (key, value) => key.toLong -> value }
      statisticsService.channelWithMostViewsForEachCategory.map {
        categoryChannelMap =>
          categoryChannelMap.flatMap { case (category, title) =>
            title.map(category.id -> _)
          } mustEqual correctChannelsForCategory
      }
    }
  }
}
