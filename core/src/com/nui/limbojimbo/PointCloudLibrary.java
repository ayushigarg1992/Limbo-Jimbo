/**
 * The $P Point-Cloud Recognizer (Java version)
 *
 *  by David White
 *  Copyright (c) 2012, David White. All rights reserved.
 *
 *  based entirely on the $P Point-Cloud Recognizer (Javascript version)
 *  found at http://depts.washington.edu/aimgroup/proj/dollar/pdollar.html
 *  who's original header follows:
 *
 *************************************************************************
 * The $P Point-Cloud Recognizer (JavaScript version)
 *
 *  Radu-Daniel Vatavu, Ph.D.
 *  University Stefan cel Mare of Suceava
 *  Suceava 720229, Romania
 *  vatavu@eed.usv.ro
 *
 *  Lisa Anthony, Ph.D.
 *      UMBC
 *      Information Systems Department
 *      1000 Hilltop Circle
 *      Baltimore, MD 21250
 *      lanthony@umbc.edu
 *
 *  Jacob O. Wobbrock, Ph.D.
 *  The Information School
 *  University of Washington
 *  Seattle, WA 98195-2840
 *  wobbrock@uw.edu
 *
 * The academic publication for the $P recognizer, and what should be
 * used to cite it, is:
 *
 *  Vatavu, R.-D., Anthony, L. and Wobbrock, J.O. (2012).
 *    Gestures as point clouds: A $P recognizer for user interface
 *    prototypes. Proceedings of the ACM Int'l Conference on
 *    Multimodal Interfaces (ICMI '12). Santa Monica, California
 *    (October 22-26, 2012). New York: ACM Press, pp. 273-280.
 *
 * This software is distributed under the "New BSD License" agreement:
 *
 * Copyright (c) 2012, Radu-Daniel Vatavu, Lisa Anthony, and
 * Jacob O. Wobbrock. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    * Neither the names of the University Stefan cel Mare of Suceava,
 *  University of Washington, nor UMBC, nor the names of its contributors
 *  may be used to endorse or promote products derived from this software
 *  without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Radu-Daniel Vatavu OR Lisa Anthony
 * OR Jacob O. Wobbrock BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
**/
package com.nui.limbojimbo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class PointCloudLibrary
{
  private static double CLOSE_ENOUGH = 0.3;
  private static PointCloudLibrary demoLibrary = null;
  private ArrayList<PointCloud> _pointClouds =  new ArrayList<PointCloud>();

  public PointCloudLibrary()
  {
  }

  public PointCloudLibrary(ArrayList<PointCloud> pointClouds)
  {
    if(null == pointClouds)
    {
      throw new IllegalArgumentException("Point clouds cannot be null");
    }
    
    _pointClouds = pointClouds;
  }

  // the following is NOT part of the originally published javascript implementation
  // and has been added to support addition of directional testing for point clouds
  // which represent unistroke gestures
  public boolean containsOnlyUnistrokes()
  {
    for(int i = 0; i < _pointClouds.size(); i++)
    {
      if(!_pointClouds.get(i).isUnistroke())
      {
        return false;
      }
    }
    
    return true;
  }

  Set<PointCloud> getPointCloud(String name)
  {
    HashSet<PointCloud> result = new HashSet<PointCloud>();
    
    for(int i = 0; i < _pointClouds.size(); i++)
    {
      if(_pointClouds.get(i).getName().equals(name))
      {
        result.add(_pointClouds.get(i));
      }
    }
    
    return result;
  }
  
  public Set<String> getNames()
  {
    HashSet<String> result = new HashSet<String>();
    for(int i = 0; i < _pointClouds.size(); i++)
    {
      result.add(_pointClouds.get(i).getName());
    }
    
    return result;
  }
  
  public void addPointCloud(PointCloud cloud)
  {
    _pointClouds.add(cloud);
  }
  
  // removes one or more point clouds carrying the specified name (which
  // is case sensitive) from the library. if no matches are found, null is
  // returned, else those removed are returned
  public ArrayList<PointCloud> removePointCloud(String name)
  {
    ArrayList<PointCloud> result = null;
    
    if(null == name || "" == name)
    {
      throw new IllegalArgumentException("Name must be provided");
    }
    
    for(int i = 0; i < _pointClouds.size(); i++)
    {
      PointCloud p = _pointClouds.get(i);
      if(name != p.getName())
      {
        continue;
      }
      
      if(result == null)
      {
        result = new ArrayList<PointCloud>();
        result.add(p);
        _pointClouds.remove(i);
      }
    }
    
    return result;
  }
  
  public void clear()
  {
    if(this == demoLibrary)
    {
      _pointClouds = new ArrayList<PointCloud>();
      populateDemoLibrary(this);
    }
    else
    {
      _pointClouds = new ArrayList<PointCloud>();
    }
  }
  
  public int getSize()
  {
    return _pointClouds.size();
  }

  // most closely matches published javascript implementation of $P,
  // returns only the single, best match. note that the score member of
  // the result contains the aggregate distances between the two gestures.
  // for the original implementation see originalRecognize() below.
  public PointCloudMatchResult recognize(PointCloud inputGesture)
  {
    return recognize(inputGesture, false);
  }

  // as with published javascript implementation of $P, this returns only
  // the single, best match. however it permits use of directional testing
  // and, as such, should be used only with unistrokes. note that the score
  // member of the result contains the aggregate distances between the two
  // gestures. for the original implementation see originalRecognize() below.
  public PointCloudMatchResult recognize(PointCloud inputGesture, boolean testDirectionality)
  {
    return recognizeAll(inputGesture, testDirectionality)[0];
  }

  // unlike the published javascript implementation of $P, this returns an array
  // of results - one for each point cloud in the library sorted in order of
  // increasing aggregate distance between the point clouds. it also permits use of
  // some simple directional testing which should be used only with unistrokes. note
  // that the score member of the result contains the aggregate distance between the
  // two gestures. for the original implementation see originalRecognize() below.
  public PointCloudMatchResult[] recognizeAll(PointCloud inputGesture, boolean testDirectionality)
  {
    // the following is NOT part of the originally published javascript implementation
    // and has been added to support addition of directional testing for point clouds
    // which represent unistroke gestures
    if(testDirectionality)
    {
      if(!inputGesture.isUnistroke())
      {
        throw new IllegalArgumentException("If testDirectionality is true, input gesture must contain a unistroke");
      }

      if(! containsOnlyUnistrokes())
      {
        throw new IllegalArgumentException("If testDirectionality is true, the point cloud library must contain only unistroke point clouds");
      }
    }

    double b = Double.POSITIVE_INFINITY;
    int u = -1;
    PointCloudMatchResult[] results = new PointCloudMatchResult[_pointClouds.size()];

    for(int i = 0; i < _pointClouds.size(); i++) // for each point-cloud template
    {
      PointCloud pointCloud = _pointClouds.get(i);

      // the following is NOT part of the originally published javascript implementation
      // and has been added to support addition of directional testing for point clouds
      // which represent unistroke gestures
      if(testDirectionality)
      {
        // test to see if the gestures match roughly in directionality
        // if not, keep looking
        PointCloudPoint refStart = pointCloud.getFirstPoint();
        PointCloudPoint inStart = inputGesture.getFirstPoint();
        PointCloudPoint refEnd = pointCloud.getLastPoint();
        PointCloudPoint inEnd = inputGesture.getLastPoint();
        
        if((PointCloudUtils.distance(refStart, inStart) > CLOSE_ENOUGH) ||
           (PointCloudUtils.distance(refEnd, inEnd) > CLOSE_ENOUGH))
        {
          results[i] = new PointCloudMatchResult(pointCloud.getName(), Double.POSITIVE_INFINITY);
          continue;
        }
      }

      double d = pointCloud.greedyMatch(inputGesture);
      results[i] = new PointCloudMatchResult(pointCloud.getName(), d);
    }

    Arrays.sort(results, new Comparator<PointCloudMatchResult>()
    {
       public int compare(PointCloudMatchResult obj1, PointCloudMatchResult obj2)
        {
          if(obj1.getScore() < obj2.getScore())
          {
            return -1;
          }
  
          if(obj1.getScore() > obj2.getScore())
          {
            return 1;
          }
  
          return 0;
        }
    });

    return results;
  }
  
  /* this method implements the recognize routine as originally published in
   * javascript. in this author's experience:
  
   * (a) the score being normalized between 0 and 1 offered little meaning or
   * relationship to the quality of the match as implied by the word "score".
   * "correct" matches were found to result with scores at both extremes of the
   * range.
   * 
   * (b) on occasion it was helpful to have the results of matches for each of
   * the point clouds in the library. the original implementation provides only
   * the "best" match.
   * 
   * (c) $P as published implements directional invariance and does so by design.
   * the main rationale for this decision stems from the value of directional invariance
   * when the recognizer is used with multi-stroke gestures. however, this attribute
   * of the recognizer renders it incapable of discerning similar but semantically
   * different unistroke gestures such as a single top->bottom or left->right
   * from a single bottom->top or right->left. 
   */
  public PointCloudMatchResult originalRecognize(PointCloud inputGesture)
  {
    double b = Double.POSITIVE_INFINITY;
    int u = -1;

    for(int i = 0; i < _pointClouds.size(); i++) // for each point-cloud template
    {
      PointCloud pointCloud = _pointClouds.get(i);
      double d = inputGesture.greedyMatch(pointCloud);
      
      if (d < b)
      {
        b = d; // best (least) distance
        u = i; // point-cloud
      }
    }

    if(u == -1)
    {
      return new PointCloudMatchResult("No match", 0.0);
    }
    else
    {
      double r = Math.max((b - 2.0) / -2.0, 0.0);
      return new PointCloudMatchResult(_pointClouds.get(u).getName(), r);
    }
  }
  
  public static PointCloudLibrary getDemoLibrary()
  {
    if(null != demoLibrary)
    {
      return demoLibrary;
    }

    demoLibrary = new PointCloudLibrary();
    populateDemoLibrary(demoLibrary);
    return demoLibrary;
  }
  
  private static void populateDemoLibrary(PointCloudLibrary library)
  {

    ArrayList<PointCloudPoint> points = new ArrayList<PointCloudPoint>();



    points = new ArrayList<PointCloudPoint>();
   // points.add(new PointCloudPoint(177,92,1));
    points.add(new PointCloudPoint(739.2857055664063,839.1428833007813,1));
    points.add(new PointCloudPoint(726.898193359375,830.679443359375,1));
    points.add(new PointCloudPoint(717.5223388671875,818.9786376953125,1));
    points.add(new PointCloudPoint(717.5223388671875,818.9786376953125,1));
    points.add(new PointCloudPoint(700.1646118164063,801.5866088867188,1));
    points.add(new PointCloudPoint(679.7136840820313,781.9536743164063,1));
    points.add(new PointCloudPoint(661.5307006835938,765.3656616210938,1));
    points.add(new PointCloudPoint(644.5671997070313,753.3801879882813,1));
    points.add(new PointCloudPoint(633.0413208007813,746.5746459960938,1));
    points.add(new PointCloudPoint(621.9168090820313,742.3018188476563,1));
    points.add(new PointCloudPoint(609.0560913085938,738.040771484375,1));
    points.add(new PointCloudPoint(595.4257202148438,735.8395385742188,1));
    points.add(new PointCloudPoint(584.8214111328125,734.142822265625,1));
    points.add(new PointCloudPoint(576.102783203125,733.7142944335938,1));
    points.add(new PointCloudPoint(559.2666015625,734.4237060546875,1));
    points.add(new PointCloudPoint(541.147216796875,736.910400390625,1));
    points.add(new PointCloudPoint(526.4202880859375,741.2598266601563,1));
    points.add(new PointCloudPoint(513.2423706054688,745.4767456054688,1));
    points.add(new PointCloudPoint(499.61480712890625,751.4706420898438,1));
    points.add(new PointCloudPoint(482.4585266113281,759.2770385742188,1));
    points.add(new PointCloudPoint(474.3251953125,763.4722290039063,1));
    points.add(new PointCloudPoint(459.31365966796875,773.2461547851563,1));
    points.add(new PointCloudPoint(444.23431396484375,783.9137573242188,1));
    points.add(new PointCloudPoint(432.3987731933594,793.0034790039063,1));
    points.add(new PointCloudPoint(420.7851257324219,803.7605590820313,1));
    points.add(new PointCloudPoint(409.3700866699219,814.718994140625,1));
    points.add(new PointCloudPoint(399.9681091308594,825.6810913085938,1));
    points.add(new PointCloudPoint(395.4505615234375,831.1022338867188,1));
    points.add(new PointCloudPoint(385.3146057128906,844.8228759765625,1));
    points.add(new PointCloudPoint(375.4456481933594,860.8941650390625,1));
    points.add(new PointCloudPoint(368.054931640625,874.7630615234375,1));
    points.add(new PointCloudPoint(360.8755187988281,888.547607421875,1));
    points.add(new PointCloudPoint(354.5860290527344,904.259765625,1));
    points.add(new PointCloudPoint(349.5200500488281,922.4144287109375,1));
    points.add(new PointCloudPoint(347.79144287109375,931.081787109375,1));
    points.add(new PointCloudPoint(345.53570556640625,957.5452880859375,1));
    points.add(new PointCloudPoint(346.2029113769531,986.543212890625,1));
    points.add(new PointCloudPoint(348.5085144042969,1012.2564697265625,1));
    points.add(new PointCloudPoint(351.6512756347656,1030.6265869140625,1));
    points.add(new PointCloudPoint(356.91455078125,1049.3424072265625,1));
    points.add(new PointCloudPoint(356.91455078125,1049.3424072265625,1));
    points.add(new PointCloudPoint(356.91455078125,1049.3424072265625,1));
    points.add(new PointCloudPoint(365.8670959472656,1071.1685791015625,1));
    points.add(new PointCloudPoint(373.08990478515625,1082.645751953125,1));
    points.add(new PointCloudPoint(394.71563720703125,1108.1026611328125,1));
    points.add(new PointCloudPoint(412.60333251953125,1126.3848876953125,1));
    points.add(new PointCloudPoint(434.0748291015625,1142.2996826171875,1));
    points.add(new PointCloudPoint(449.8406982421875,1154.5869140625,1));
    points.add(new PointCloudPoint(465.17205810546875,1165.603271484375,1));
    points.add(new PointCloudPoint(487.74151611328125,1176.5445556640625,1));
    points.add(new PointCloudPoint(495.4876403808594,1180.7491455078125,1));
    points.add(new PointCloudPoint(518.6475219726563,1190.7314453125,1));
    points.add(new PointCloudPoint(546.789306640625,1198.69189453125,1));
    points.add(new PointCloudPoint(563.4201049804688,1202.5758056640625,1));
    points.add(new PointCloudPoint(583.4105224609375,1202.8985595703125,1));
    points.add(new PointCloudPoint(604.8225708007813,1200.70703125,1));
    points.add(new PointCloudPoint(619.5180053710938,1197.79541015625,1));
    points.add(new PointCloudPoint(626.6566162109375,1195.755615234375,1));
    points.add(new PointCloudPoint(645.030517578125,1187.59716796875,1));
    points.add(new PointCloudPoint(665.0040283203125,1178.2265625,1));
    points.add(new PointCloudPoint(675.6746826171875,1171.6571044921875,1));
    points.add(new PointCloudPoint(684.7871704101563,1165.09619140625,1));
    points.add(new PointCloudPoint(693.6414794921875,1157.2470703125,1));
    points.add(new PointCloudPoint(704.17724609375,1146.275634765625,1));
    points.add(new PointCloudPoint(710.7544555664063,1139.96142578125,1));
    points.add(new PointCloudPoint(720.0523681640625,1129.1639404296875,1));
    points.add(new PointCloudPoint(729.9585571289063,1116.1455078125,1));
    points.add(new PointCloudPoint(737.9937744140625,1101.337646484375,1));
    points.add(new PointCloudPoint(743.1031494140625,1090.1239013671875,1));
    points.add(new PointCloudPoint(747.722412109375,1079.03759765625,1));
    points.add(new PointCloudPoint(752.2559814453125,1068.1571044921875,1));
    points.add(new PointCloudPoint(755.357177734375,1060.71435546875,1));
    points.add(new PointCloudPoint(756.6675415039063,1051.0233154296875,1));
    points.add(new PointCloudPoint(760.6219482421875,1033.477783203125,1));
    points.add(new PointCloudPoint(762.8569946289063,1020.8577880859375,1));
    points.add(new PointCloudPoint(764.2857055664063,1009.9654541015625,1));
    points.add(new PointCloudPoint(764.2857055664063,999.0948486328125,1));
    points.add(new PointCloudPoint(763.3787841796875,988.2183837890625,1));
    points.add(new PointCloudPoint(761.1639404296875,977.5872802734375,1));
    points.add(new PointCloudPoint(759.6194458007813,964.1732177734375,1));
    points.add(new PointCloudPoint(758.2135620117188,953.1392822265625,1));
    points.add(new PointCloudPoint(753.7250366210938,934.0185546875,1));
    points.add(new PointCloudPoint(751.4388427734375,925.572509765625,1));
    points.add(new PointCloudPoint(749.2598266601563,919.2969970703125,1));
    points.add(new PointCloudPoint(746.0070190429688,907.809814453125,1));
    points.add(new PointCloudPoint(743.8397216796875,900.3446044921875,1));
    points.add(new PointCloudPoint(741.6522827148438,893.95849609375,1));
    points.add(new PointCloudPoint(739.42431640625,887.5419921875,1));
    points.add(new PointCloudPoint(737.2003173828125,881.136962890625,1));
    points.add(new PointCloudPoint(733.4293212890625,873.3271484375,1));
    points.add(new PointCloudPoint(729.3090209960938,866.42236328125,1));

    library.addPointCloud(new PointCloud("O", points));

   /*

    points.add(new PointCloudPoint(562.5,671.1,1));
    points.add(new PointCloudPoint(557.1,684.8,1));
    points.add(new PointCloudPoint( 554.4,695.2,1));
    points.add(new PointCloudPoint( 554.4,708.1,1));
    points.add(new PointCloudPoint( 554.4,731.9,1));
    points.add(new PointCloudPoint( 554.4,767.9,1));
    points.add(new PointCloudPoint(  554.48,767.9,1));
    points.add(new PointCloudPoint( 554.4,767.9,1));
    points.add(new PointCloudPoint(554.4642944335938,783.1931762695313,1));
    points.add(new PointCloudPoint(554.4642944335938,792.9080200195313,1));
    points.add(new PointCloudPoint(554.662109375,811.6114501953125,1));
    points.add(new PointCloudPoint(556.25,836.41796875,1));
    points.add(new PointCloudPoint(556.25,856.072265625,1));
    points.add(new PointCloudPoint(556.25,875.676513671875,1));
    points.add(new PointCloudPoint(556.25,894.07470703125,1));
    points.add(new PointCloudPoint(556.25,911.6546630859375,1));
    points.add(new PointCloudPoint(554.784912109375,922.394775390625,1));
    points.add(new PointCloudPoint(553.0364990234375,933.5946044921875,1));
    points.add(new PointCloudPoint(551.54443359375,955.852783203125,1));
    points.add(new PointCloudPoint(549.2642822265625,972.658203125,1));
    points.add(new PointCloudPoint(547.3214111328125,986.743408203125,1));
    points.add(new PointCloudPoint(546.4285888671875,999.83837890625,1));
    points.add(new PointCloudPoint(545.5357055664063,1012.984619140625,1));
    points.add(new PointCloudPoint(545.5357055664063,1022.1531982421875,1));
    points.add(new PointCloudPoint(545.3238525390625,1030.8521728515625,1));
    points.add(new PointCloudPoint(543.75,1045.8154296875,1));
    points.add(new PointCloudPoint(543.5817260742188,1058.5218505859375,1));
    points.add(new PointCloudPoint(542.8571166992188,1067.7110595703125,1));
    points.add(new PointCloudPoint(541.9642944335938,1076.329833984375,1));
    points.add(new PointCloudPoint(541.2647094726563,1084.5860595703125,1));
    points.add(new PointCloudPoint(540.1785888671875,1091.158447265625,1));
    points.add(new PointCloudPoint(538.392822265625,1100.865966796875,1));
    points.add(new PointCloudPoint(538.392822265625,1107.41455078125,1));
    points.add(new PointCloudPoint(538.392822265625,1113.03369140625,1));
    points.add(new PointCloudPoint(538.392822265625,1121.7130126953125,1));
    points.add(new PointCloudPoint(537.7677001953125,1128.6002197265625,1));
    library.addPointCloud(new PointCloud("|", points));

   points = new ArrayList<PointCloudPoint>();
    points.add(new PointCloudPoint(856.25,1042.2857666015625,1));
    points.add(new PointCloudPoint(844.9260864257813,1048.8709716796875,1));
    points.add(new PointCloudPoint(837.8392333984375,1050.0,1));
    points.add(new PointCloudPoint(836.3110385664942,1050.19379452278,1));
    points.add(new PointCloudPoint(831.079833984375,1050.857177734375,1));
    points.add(new PointCloudPoint(825.5119018554688,1052.5714111328125,1));
    points.add(new PointCloudPoint(819.7822265625,1054.52685546875,1));
    points.add(new PointCloudPoint(815.3410560627532,1055.948004673643,1));
    points.add(new PointCloudPoint(812.9400634765625,1056.71630859375,1));
    points.add(new PointCloudPoint(806.0888671875,1057.71435546875,1));
    points.add(new PointCloudPoint(799.1973266601563,1057.71435546875,1));
    points.add(new PointCloudPoint( 793.7844807870316,1058.570574924198,1));
    points.add(new PointCloudPoint(788.3602905273438,1059.4285888671875,1));
    points.add(new PointCloudPoint(776.9987182617188,1060.2857666015625,1));
    points.add(new PointCloudPoint(772.0680910736762,1060.2857666015625,1));
    points.add(new PointCloudPoint(765.5266723632813,1060.2857666015625,1));
    points.add(new PointCloudPoint(756.2601318359375,1060.2857666015625,1));
    points.add(new PointCloudPoint(750.2519699324964,1060.2857666015625,1));
    points.add(new PointCloudPoint(747.0623779296875,1060.2857666015625,1));
    points.add(new PointCloudPoint(736.990478515625,1060.2857666015625,1));
    points.add(new PointCloudPoint(729.017822265625,1060.2857666015625,1));
    points.add(new PointCloudPoint(728.4358487913166,1060.2857666015625,1));
    points.add(new PointCloudPoint(720.2515869140625,1060.2857666015625,1));
    points.add(new PointCloudPoint(706.6197276501368,1060.2857666015625,1));
    points.add(new PointCloudPoint(706.3898315429688,1060.2857666015625,1));
    points.add(new PointCloudPoint(695.4320678710938,1060.2857666015625,1));
    points.add(new PointCloudPoint(685.8636474609375,1060.2857666015625,1));
    points.add(new PointCloudPoint(684.803606508957,1060.2857666015625,1));
    points.add(new PointCloudPoint(676.3084106445313,1060.2857666015625,1));
    points.add(new PointCloudPoint(665.9872436523438,1060.2857666015625,1));
    points.add(new PointCloudPoint(662.9874853677773,1060.2857666015625,1));
    points.add(new PointCloudPoint(658.482177734375,1060.2857666015625,1));
    points.add(new PointCloudPoint(649.3257446289063,1060.2857666015625,1));
    points.add(new PointCloudPoint(641.1713642265975,1060.2857666015625,1));
    points.add(new PointCloudPoint(637.7556762695313,1060.2857666015625,1));
    points.add(new PointCloudPoint(628.1876831054688,1060.2857666015625,1));
    points.add(new PointCloudPoint(619.3552430854177,1060.2857666015625,1));
    points.add(new PointCloudPoint(618.6480102539063,1060.2857666015625,1));
    points.add(new PointCloudPoint(610.1547241210938,1060.2857666015625,1));
    points.add(new PointCloudPoint(600.4151611328125,1060.2857666015625,1));
    points.add(new PointCloudPoint(597.5391219442379,1060.2857666015625,1));
    points.add(new PointCloudPoint(594.66015625,1060.2857666015625,1));
    points.add(new PointCloudPoint(586.3969116210938,1060.2857666015625,1));
    points.add(new PointCloudPoint(576.8145141601563,1060.2857666015625,1));
    points.add(new PointCloudPoint(575.7230008030581,1060.2857666015625,1));
    points.add(new PointCloudPoint(567.6446533203125,1060.2857666015625,1));
    points.add(new PointCloudPoint( 561.092529296875,1060.2857666015625,1));
    points.add(new PointCloudPoint(553.9068796618783,1060.2857666015625,1));
    points.add(new PointCloudPoint(550.9114379882813,1060.2857666015625,1));
    points.add(new PointCloudPoint(541.1072387695313,1060.2857666015625,1));
    points.add(new PointCloudPoint(532.0907585206985,1060.2857666015625,1));
    points.add(new PointCloudPoint(527.351806640625,1060.2857666015625,1));
    points.add(new PointCloudPoint(518.7672119140625,1060.2857666015625,1));
    points.add(new PointCloudPoint(510.7960205078125,1060.2857666015625,1));
    points.add(new PointCloudPoint(510.27463737951877,1060.2857666015625,1));
    points.add(new PointCloudPoint(503.7122497558594,1060.2857666015625,1));
    points.add(new PointCloudPoint(495.74591064453125,1060.2857666015625,1));
    points.add(new PointCloudPoint(488.45851623833903,1060.2857666015625,1));
    points.add(new PointCloudPoint(485.57037353515625,1060.2857666015625,1));
    points.add(new PointCloudPoint(472.4384460449219,1059.6710205078125,1));
    points.add(new PointCloudPoint(466.70661363945345,1058.9135231385628,1));
    points.add(new PointCloudPoint(464.117919921875,1058.5714111328125,1));
    points.add(new PointCloudPoint(456.9204406738281,1057.6431884765625,1));
    points.add(new PointCloudPoint(449.7649230957031,1057.71435546875,1));
    points.add(new PointCloudPoint(444.9729619345231,1057.71435546875,1));
    points.add(new PointCloudPoint(441.73455810546875,1057.71435546875,1));
    points.add(new PointCloudPoint(432.672119140625,1056.7698974609375,1));
    points.add(new PointCloudPoint(423.22092727158605,1056.2371128579343,1));
    points.add(new PointCloudPoint(418.6401062011719,1055.9788818359375,1));
    points.add(new PointCloudPoint(409.1212158203125,1054.5462646484375,1));
    points.add(new PointCloudPoint(401.570229544088,1053.667622573035,1));
    points.add(new PointCloudPoint(399.5159912109375,1053.4285888671875,1));
    points.add(new PointCloudPoint(390.8966979980469,1053.4285888671875,1));
    points.add(new PointCloudPoint(382.23046875,1053.4285888671875,1));
    points.add(new PointCloudPoint(379.7679687719275,1053.4285888671875,1));
    points.add(new PointCloudPoint(372.7135314941406,1053.4285888671875,1));
    points.add(new PointCloudPoint(367.36383056640625,1053.4285888671875,1));
    points.add(new PointCloudPoint(357.95184763074775,1053.4285888671875,1));
    points.add(new PointCloudPoint(355.9783630371094,1053.4285888671875,1));
    points.add(new PointCloudPoint(346.3907775878906,1052.5714111328125,1));
    points.add(new PointCloudPoint(336.8554992675781,1052.5714111328125,1));
    points.add(new PointCloudPoint(336.17396819516455,1052.5714111328125,1));
    points.add(new PointCloudPoint(327.2978515625,1052.5714111328125,1));
    points.add(new PointCloudPoint(317.7689208984375,1052.5714111328125,1));
    points.add(new PointCloudPoint(314.3578470539848,1052.5714111328125,1));
    points.add(new PointCloudPoint(309.0515441894531,1052.571411132812,1));
    points.add(new PointCloudPoint(303.72344970703125,1052.57141113281,1));
    points.add(new PointCloudPoint(292.5417259128051,1052.5714111328125,1));
    points.add(new PointCloudPoint(291.7845458984375,1052.5714111328125,1));
    points.add(new PointCloudPoint(281.923095703125,1052.5714111328125,1));
    points.add(new PointCloudPoint(272.3529968261719,1052.5714111328125,1));
    points.add(new PointCloudPoint(270.72560477162534,1052.5714111328125,1));
    points.add(new PointCloudPoint(262.7483215332031,1052.5714111328125,1));
    points.add(new PointCloudPoint(253.16091918945313,1052.88427734375,1));
    points.add(new PointCloudPoint(249.03184314601572,1053.8752701364917,1));
    points.add(new PointCloudPoint(245.53570556640625,1054.71435546875,1));
    points.add(new PointCloudPoint(239.95864868164063,1054.2857666015625,1));
    points.add(new PointCloudPoint(231.2477569580078,1054.2857666015625,1));
    points.add(new PointCloudPoint(227.33144801400718,1054.2857666015625,1));
    points.add(new PointCloudPoint(224.0401153564453,1054.2857666015625,1));
    points.add(new PointCloudPoint(216.8584442138672,1054.2857666015625,1));
    points.add(new PointCloudPoint(211.50137329101563,1054.2857666015625,1));
    points.add(new PointCloudPoint(205.51532687282744,1054.2857666015625,1));
    points.add(new PointCloudPoint(203.5714111328125,1054.2857666015625,1));
    points.add(new PointCloudPoint(194.918701171875,1054.2857666015625,1));
    points.add(new PointCloudPoint(189.54071044921875,1054.2857666015625,1));
    points.add(new PointCloudPoint(183.76242065429688,1055.142822265625,1));
    library.addPointCloud(new PointCloud("_", points));

    points = new ArrayList<PointCloudPoint>();
    points.add(new PointCloudPoint(838.392822265625,1254.857177734375,1));
    points.add(new PointCloudPoint(826.076171875,1245.9488525390625,1));
    points.add(new PointCloudPoint(818.2620239257813,1233.4659423828125,1));
    points.add(new PointCloudPoint(808.0635375976563,1215.1917724609375,1));
    points.add(new PointCloudPoint(796.604248046875,1192.623046875,1));
    points.add(new PointCloudPoint(786.8416748046875,1172.597412109375,1));
    points.add(new PointCloudPoint(775.8956298828125,1147.9359130859375,1));
    points.add(new PointCloudPoint(769.7757568359375,1133.5950927734375,1));
    points.add(new PointCloudPoint(758.1771850585938,1109.4144287109375,1));
    points.add(new PointCloudPoint(737.4002075195313,1070.8243408203125,1));
    points.add(new PointCloudPoint(724.18115234375,1047.558837890625,1));
    points.add(new PointCloudPoint(710.6219482421875,1024.1380615234375,1));
    points.add(new PointCloudPoint(698.8673706054688,1006.7745361328125,1));
    points.add(new PointCloudPoint(685.8128662109375,985.67529296875,1));
    points.add(new PointCloudPoint(678.5714111328125,974.142822265625,1));
    points.add(new PointCloudPoint(672.1404418945313,963.6995849609375,1));
    points.add(new PointCloudPoint(660.4900512695313,945.6947021484375,1));
    points.add(new PointCloudPoint(651.7987060546875,932.020751953125,1));
    points.add(new PointCloudPoint( 645.2835083007813,923.1058349609375,1));
    points.add(new PointCloudPoint(638.7623901367188,916.640380859375,1));
    points.add(new PointCloudPoint(634.591552734375,910.9222412109375,1));
    points.add(new PointCloudPoint(628.079833984375,906.6212158203125,1));
    points.add(new PointCloudPoint( 620.0421752929688,902.5714111328125,1));
    points.add(new PointCloudPoint(612.77978515625,902.86572265625,1));
    points.add(new PointCloudPoint(603.7747802734375,909.2333984375,1));
    points.add(new PointCloudPoint(595.7284545898438,919.075439453125,1));
    points.add(new PointCloudPoint(581.93994140625,940.9285888671875,1));
    points.add(new PointCloudPoint(568.7431640625,964.2987060546875,1));
    points.add(new PointCloudPoint(559.4212646484375,982.1968994140625,1));
    points.add(new PointCloudPoint(538.3890380859375,1027.722412109375,1));
    points.add(new PointCloudPoint(533.9329223632813,1039.1317138671875,1));
    points.add(new PointCloudPoint(525.9840087890625,1054.9678955078125,1));
    points.add(new PointCloudPoint(512.807861328125,1081.406982421875,1));
    points.add(new PointCloudPoint(503.6593017578125,1101.20361328125,1));
    points.add(new PointCloudPoint(496.7008972167969,1114.6199951171875,1));
    points.add(new PointCloudPoint(489.8678283691406,1127.7393798828125,1));
    points.add(new PointCloudPoint(483.0085754394531,1140.9091796875,1));
    points.add(new PointCloudPoint(478.125,1150.285888671875,1));
    points.add(new PointCloudPoint(472.66558837890625,1159.21875,1));
    points.add(new PointCloudPoint(462.87786865234375,1175.2745361328125,1));
    points.add(new PointCloudPoint(455.9281005859375,1187.08642578125,1));
    points.add(new PointCloudPoint(450.2879943847656,1196.875732421875,1));
    points.add(new PointCloudPoint(445.69122314453125,1204.9188232421875,1));
    points.add(new PointCloudPoint(441.111328125,1211.5140380859375,1));
    points.add(new PointCloudPoint(437.5,1216.71435546875,1));
    points.add(new PointCloudPoint(434.0690002441406,1223.7303466796875,1));

    library.addPointCloud(new PointCloud("^", points));

    points = new ArrayList<PointCloudPoint>();
    points.add(new PointCloudPoint(557.142822265625,608.5714111328125,1));
    points.add(new PointCloudPoint(548.715576171875,619.6090087890625,1));
    points.add(new PointCloudPoint(545.5669555664063,626.5113525390625,1));
    points.add(new PointCloudPoint(540.5361328125,639.1990966796875,1));
    points.add(new PointCloudPoint(533.484130859375,658.7086791992188,1));
    points.add(new PointCloudPoint(527.2117919921875,676.77294921875,1));
    points.add(new PointCloudPoint(518.7801513671875,701.0560913085938,1));
    points.add(new PointCloudPoint(511.423095703125,722.2443237304688,1));
    points.add(new PointCloudPoint(506.55950927734375,736.2515258789063,1));
    points.add(new PointCloudPoint(497.51788330078125,760.95849609375,1));
    points.add(new PointCloudPoint(488.3642883300781,789.5177001953125,1));
    points.add(new PointCloudPoint(478.9135437011719,816.7289428710938,1));
    points.add(new PointCloudPoint(469.74176025390625,844.19140625,1));
    points.add(new PointCloudPoint(460.2784729003906,872.1123046875,1));
    points.add(new PointCloudPoint(450.9486083984375,898.9822998046875,1));
    points.add(new PointCloudPoint(445.9574890136719,912.600830078125,1));
    points.add(new PointCloudPoint(438.99420166015625,934.8365478515625,1));
    points.add(new PointCloudPoint(431.4991760253906,964.18603515625,1));
    points.add(new PointCloudPoint(425.7621765136719,985.3590087890625,1));
    points.add(new PointCloudPoint(421.0060119628906,1000.848388671875,1));
    points.add(new PointCloudPoint(414.8001403808594,1017.825927734375,1));
    points.add(new PointCloudPoint(410.2057189941406,1030.893310546875,1));
    points.add(new PointCloudPoint( 407.4871520996094,1038.7227783203125,1));
    points.add(new PointCloudPoint(404.29266357421875,1047.23046875,1));
    points.add(new PointCloudPoint(402.0135803222656,1054.486572265625,1));
    points.add(new PointCloudPoint(402.5806579589844,1062.76318359375,1));
    points.add(new PointCloudPoint(410.18450927734375,1065.4285888671875,1));
    points.add(new PointCloudPoint(415.9528503417969,1063.771240234375,1));
    points.add(new PointCloudPoint(433.4372253417969,1054.054443359375,1));
    points.add(new PointCloudPoint(454.50872802734375,1039.87158203125,1));
    points.add(new PointCloudPoint(475.03704833984375,1026.0609130859375,1));
    points.add(new PointCloudPoint(497.8119812011719,1010.586181640625,1));
    points.add(new PointCloudPoint(520.0367431640625,995.2210693359375,1));
    points.add(new PointCloudPoint(534.8214111328125,985.2857666015625,1));
    points.add(new PointCloudPoint(555.64453125,971.5303955078125,1));
    points.add(new PointCloudPoint(592.3660278320313,949.3114013671875,1));
    points.add(new PointCloudPoint(610.3507080078125,939.2606201171875,1));
    points.add(new PointCloudPoint(624.2603149414063,928.4488525390625,1));
    points.add(new PointCloudPoint(642.0784301757813,916.7342529296875,1));
    points.add(new PointCloudPoint(660.8528442382813,904.7684326171875,1));
    points.add(new PointCloudPoint(672.3214111328125,897.428466796875,1));
    points.add(new PointCloudPoint(678.0115356445313,893.8082275390625,1));
    points.add(new PointCloudPoint(689.4119873046875,886.6234130859375,1));
    points.add(new PointCloudPoint(697.5455932617188,880.999267578125,1));
    points.add(new PointCloudPoint(704.2235717773438,876.7254638671875,1));
    points.add(new PointCloudPoint(713.4925537109375,872.0950927734375,1));
    points.add(new PointCloudPoint(719.3223876953125,870.857177734375,1));
    points.add(new PointCloudPoint(721.4285888671875,876.917724609375,1));
    points.add(new PointCloudPoint(719.5769653320313,882.189697265625,1));
    points.add(new PointCloudPoint(714.0069580078125,897.5706787109375,1));
    points.add(new PointCloudPoint(701.0109252929688,935.431396484375,1));
    points.add(new PointCloudPoint(688.422119140625,967.984130859375,1));
    points.add(new PointCloudPoint(676.922119140625,997.0047607421875,1));
    points.add(new PointCloudPoint(665.5786743164063,1027.4959716796875,1));
    points.add(new PointCloudPoint(652.8538208007813,1061.129638671875,1));
    points.add(new PointCloudPoint(638.3928833007813,1095.0,1));
    points.add(new PointCloudPoint( 630.468994140625,1110.8719482,1));
    points.add(new PointCloudPoint(611.0543212890625,1151.05126953125,1));
    points.add(new PointCloudPoint(600.4767456054688,1172.51318359375,1));
    points.add(new PointCloudPoint(591.33056640625,1190.0738525390625,1));
    points.add(new PointCloudPoint(580.134521484375,1207.3704833984375,1));
    points.add(new PointCloudPoint(570.4832763671875,1224.957763671875,1));
    points.add(new PointCloudPoint(561.6070556640625,1240.71435546875,1));
    points.add(new PointCloudPoint(558.49658203125,1247.1151123046875,1));
    points.add(new PointCloudPoint(550.3939208984375,1263.5294189453125,1));
    points.add(new PointCloudPoint(541.9618530273438,1278.00390625,1));
    points.add(new PointCloudPoint(535.0,1287.6,1));
    points.add(new PointCloudPoint(528.2,1296.4,1));
    points.add(new PointCloudPoint(521.9727172851563,1304.64501953125,1));
    points.add(new PointCloudPoint(517.373779296875,1311.267333984375,1));
    points.add(new PointCloudPoint(510.58880615234375,1321.037841796875,1));
    points.add(new PointCloudPoint(506.0118408203125,1327.0858154296875,1));
    points.add(new PointCloudPoint(501.0956115722656,1335.23388671875,1));
    library.addPointCloud(new PointCloud("N", points));*/


  }
}
